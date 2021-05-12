package cc.laop.gateway.filter.rewrite;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.rewrite.CachedBodyOutputMessage;
import org.springframework.cloud.gateway.support.BodyInserterContext;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

/**
 * @Auther: peng
 * @Date: create in 2021/5/11 9:58
 * @Description: 请求体解密、解密， 以下实现参考
 * {@link org.springframework.cloud.gateway.filter.factory.rewrite.ModifyRequestBodyGatewayFilterFactory}
 */
@Component
@Deprecated
public class ModifyRequestGatewayFilterFactory extends AbstractGatewayFilterFactory<Config> {

    private List<HttpMessageReader<?>> messageReaders;

    public ModifyRequestGatewayFilterFactory() {
        super(Config.class);
        this.messageReaders = HandlerStrategies.withDefaults().messageReaders();
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("handler", "contentType");
    }

    @Override
    public GatewayFilter apply(Config config) {

        return (exchange, chain) -> {
            ServerRequest serverRequest = ServerRequest.create(exchange,
                    messageReaders);

            // 解码、解密
            Mono modifiedBody = serverRequest.bodyToMono(String.class)
                    .flatMap(originalBody ->
                            Mono.just(RewriteHandler.getHandler(config.getHandler()).decode(originalBody)));

            HttpHeaders headers = new HttpHeaders();
            headers.putAll(exchange.getRequest().getHeaders());
            headers.remove(HttpHeaders.CONTENT_LENGTH);
            if (config.getContentType() != null) {
                headers.set(HttpHeaders.CONTENT_TYPE, config.getContentType());
            }

            CachedBodyOutputMessage outputMessage = new CachedBodyOutputMessage(exchange, headers);
            return BodyInserters
                    .fromPublisher(modifiedBody, String.class)
                    .insert(outputMessage, new BodyInserterContext())
                    .then(Mono.defer(() -> {
                        ServerHttpRequest decorator = new ServerHttpRequestDecorator(exchange.getRequest()) {
                            @Override
                            public HttpHeaders getHeaders() {
                                long contentLength = headers.getContentLength();
                                HttpHeaders httpHeaders = new HttpHeaders();
                                httpHeaders.putAll(headers);
                                if (contentLength > 0) {
                                    httpHeaders.setContentLength(contentLength);
                                } else {
                                    httpHeaders.set(HttpHeaders.TRANSFER_ENCODING, "chunked");
                                }
                                return httpHeaders;
                            }

                            @Override
                            public Flux<DataBuffer> getBody() {
                                return outputMessage.getBody();
                            }
                        };
                        return chain.filter(exchange.mutate().request(decorator).build());
                    }));
        };

    }

}
