package cc.laop.gateway.filter.rewrite;

import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.rewrite.CachedBodyOutputMessage;
import org.springframework.cloud.gateway.filter.factory.rewrite.GzipMessageBodyResolver;
import org.springframework.cloud.gateway.support.BodyInserterContext;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Arrays;
import java.util.List;

import static org.springframework.cloud.gateway.filter.NettyWriteResponseFilter.WRITE_RESPONSE_FILTER_ORDER;
import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.ORIGINAL_RESPONSE_CONTENT_TYPE_ATTR;

/**
 * @Auther: peng
 * @Date: create in 2021/5/11 10:40
 * @Description: 以下实现参考 {@link org.springframework.cloud.gateway.filter.factory.rewrite.ModifyResponseBodyGatewayFilterFactory}
 */
@Component
@Deprecated
public class ModifyResponseGatewayFilterFactory extends AbstractGatewayFilterFactory<Config> {

    private List<HttpMessageReader<?>> messageReaders;

    private GzipMessageBodyResolver gzipMessageBodyResolver;

    public ModifyResponseGatewayFilterFactory() {
        super(Config.class);
        this.messageReaders = HandlerStrategies.withDefaults().messageReaders();
        this.gzipMessageBodyResolver = new GzipMessageBodyResolver();
    }


    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("handler", "contentType");
    }

    @Override
    public GatewayFilter apply(Config config) {
        return new ModifyResponseGatewayFilter(config);
    }

    protected class ModifyResponseGatewayFilter implements GatewayFilter, Ordered {

        private Config config;

        public ModifyResponseGatewayFilter(Config config) {
            this.config = config;
        }

        @Override
        public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
            return chain.filter(exchange.mutate().response(new ModifiedServerHttpResponse(exchange, config)).build());
        }

        @Override
        public int getOrder() {
            return WRITE_RESPONSE_FILTER_ORDER - 1;
        }
    }


    protected class ModifiedServerHttpResponse extends ServerHttpResponseDecorator {

        private final ServerWebExchange exchange;

        private final Config config;

        public ModifiedServerHttpResponse(ServerWebExchange exchange, Config config) {
            super(exchange.getResponse());
            this.exchange = exchange;
            this.config = config;
        }

        @Override
        public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
            String originalResponseContentType = exchange.getAttribute(ORIGINAL_RESPONSE_CONTENT_TYPE_ATTR);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add(HttpHeaders.CONTENT_TYPE, originalResponseContentType);

            ClientResponse clientResponse = ClientResponse
                    .create(exchange.getResponse().getStatusCode(), messageReaders)
                    .headers(headers -> headers.putAll(httpHeaders))
                    .body(Flux.from(body)).build();

            Mono modifiedBody = extractBody(exchange, clientResponse)
                    .flatMap(originalBody -> Mono.just(RewriteHandler.getHandler(config.getHandler()).encode(originalBody)));

            CachedBodyOutputMessage outputMessage = new CachedBodyOutputMessage(exchange,
                    exchange.getResponse().getHeaders());
            return BodyInserters
                    .fromPublisher(modifiedBody, String.class)
                    .insert(outputMessage, new BodyInserterContext())
                    .then(Mono.defer(() -> {
                        Mono<DataBuffer> messageBody = writeBody(getDelegate(), outputMessage);
                        HttpHeaders headers = getDelegate().getHeaders();
                        if (!headers.containsKey(HttpHeaders.TRANSFER_ENCODING) || headers.containsKey(HttpHeaders.CONTENT_LENGTH)) {
                            messageBody =
                                    messageBody.doOnNext(data -> headers.setContentLength(data.readableByteCount()));
                        }
                        if (config.getContentType() != null) {
                            getDelegate().getHeaders().set(HttpHeaders.CONTENT_TYPE, config.getContentType());
                        }
                        return getDelegate().writeWith(messageBody);
                    }));
        }

        @Override
        public Mono<Void> writeAndFlushWith(Publisher<? extends Publisher<? extends DataBuffer>> body) {
            return writeWith(Flux.from(body).flatMapSequential(p -> p));
        }

        private ClientResponse prepareClientResponse(Publisher<? extends DataBuffer> body,
                                                     HttpHeaders httpHeaders) {
            ClientResponse.Builder builder;
            builder = ClientResponse.create(exchange.getResponse().getStatusCode(),
                    messageReaders);
            return builder.headers(headers -> headers.putAll(httpHeaders))
                    .body(Flux.from(body)).build();
        }

        private Mono<String> extractBody(ServerWebExchange exchange,
                                         ClientResponse clientResponse) {

            List<String> encodingHeaders = exchange.getResponse().getHeaders()
                    .getOrEmpty(HttpHeaders.CONTENT_ENCODING);

            if (encodingHeaders.indexOf(gzipMessageBodyResolver.encodingType()) > -1) {
                return clientResponse.bodyToMono(byte[].class)
                        .publishOn(Schedulers.parallel())
                        .map(gzipMessageBodyResolver::decode)
                        .map(bytes -> exchange.getResponse().bufferFactory().wrap(bytes))
                        .map(buffer -> prepareClientResponse(Mono.just(buffer), exchange.getResponse().getHeaders()))
                        .flatMap(response -> response.bodyToMono(String.class));
            } else {
                return clientResponse.bodyToMono(String.class);
            }
        }


        private Mono<DataBuffer> writeBody(ServerHttpResponse httpResponse,
                                           CachedBodyOutputMessage message) {
            Mono<DataBuffer> response = DataBufferUtils.join(message.getBody());

            List<String> encodingHeaders = httpResponse.getHeaders()
                    .getOrEmpty(HttpHeaders.CONTENT_ENCODING);
            if (encodingHeaders.indexOf(gzipMessageBodyResolver.encodingType()) > -1) {
                DataBufferFactory dataBufferFactory = httpResponse.bufferFactory();
                response = response
                        .publishOn(Schedulers.parallel())
                        .map(buffer -> {
                            byte[] encodedResponse = gzipMessageBodyResolver.encode(buffer);
                            DataBufferUtils.release(buffer);
                            return encodedResponse;
                        })
                        .map(dataBufferFactory::wrap);
            }

            return response;
        }

    }
}
