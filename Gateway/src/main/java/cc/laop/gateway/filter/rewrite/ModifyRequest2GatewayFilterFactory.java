package cc.laop.gateway.filter.rewrite;

import cc.laop.gateway.constant.Constants;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.rewrite.ModifyRequestBodyGatewayFilterFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

/**
 * @Auther: peng
 * @Date: create in 2021/5/12 17:38
 * @Description: 以下实现直接引用了内部过滤器，请参考 {@link ModifyRequestBodyGatewayFilterFactory}
 */
@Component
public class ModifyRequest2GatewayFilterFactory extends AbstractGatewayFilterFactory<Config> {

    private ModifyRequestBodyGatewayFilterFactory modifyRequestBodyGatewayFilterFactory;

    public ModifyRequest2GatewayFilterFactory(ModifyRequestBodyGatewayFilterFactory modifyRequestBodyGatewayFilterFactory) {
        super(Config.class);
        this.modifyRequestBodyGatewayFilterFactory = modifyRequestBodyGatewayFilterFactory;
    }

    @Override
    public GatewayFilter apply(Config config) {
        ModifyRequestBodyGatewayFilterFactory.Config cf = new ModifyRequestBodyGatewayFilterFactory.Config();
        if (config.getContentType() != null) {
            cf.setContentType(config.getContentType());
        }
        // 默认按照使用String
        cf.setRewriteFunction(String.class, String.class,
                (serverWebExchange, s) -> {
                    String newBody = RewriteHandler.getHandler(config.getHandler()).decode(s);
                    serverWebExchange.getAttributes().put(Constants.CACHED_NEW_REQUESTBODY, newBody);
                    return Mono.just(newBody);
                });
        return modifyRequestBodyGatewayFilterFactory.apply(cf);
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("handler", "contentType");
    }
}
