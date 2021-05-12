package cc.laop.gateway.filter.rewrite;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.rewrite.ModifyResponseBodyGatewayFilterFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

/**
 * @Auther: peng
 * @Date: create in 2021/5/12 17:48
 * @Description: 以下实现直接引用了内部过滤器，{@link ModifyResponseBodyGatewayFilterFactory}
 */
@Component
public class ModifyResponse2GatewayFilterFactory extends AbstractGatewayFilterFactory<Config> {

    private ModifyResponseBodyGatewayFilterFactory modifyResponseBodyGatewayFilterFactory;

    public ModifyResponse2GatewayFilterFactory(ModifyResponseBodyGatewayFilterFactory modifyResponseBodyGatewayFilterFactory) {
        super(Config.class);
        this.modifyResponseBodyGatewayFilterFactory = modifyResponseBodyGatewayFilterFactory;
    }

    @Override
    public GatewayFilter apply(Config config) {
        ModifyResponseBodyGatewayFilterFactory.Config cf = new ModifyResponseBodyGatewayFilterFactory.Config();
        if (config.getContentType() != null) {
            // 新的ContentType没有添加到响应中，应该是Spring的bug
            cf.setNewContentType(config.getContentType());
        }
        cf.setRewriteFunction(String.class, String.class,
                (serverWebExchange, s) -> Mono.just(RewriteHandler.getHandler(config.getHandler()).encode(s)));
        return modifyResponseBodyGatewayFilterFactory.apply(cf);
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("handler", "contentType");
    }
}
