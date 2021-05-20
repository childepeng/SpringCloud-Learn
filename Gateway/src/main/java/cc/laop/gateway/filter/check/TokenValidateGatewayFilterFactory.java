package cc.laop.gateway.filter.check;

import cc.laop.gateway.constant.Constants;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;

/**
 * @Auther: peng
 * @Date: create in 2021/5/12 14:53
 * @Description: 用户校验（用户相关参数位于请求体中，可能会存在数据加密，因此需要先解密）
 */
@Component
public class TokenValidateGatewayFilterFactory extends AbstractGatewayFilterFactory<TokenValidateGatewayFilterFactory.Config> {

    public TokenValidateGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpResponse response = exchange.getResponse();
            // 由于同一个Request对象，RequestBody只能读取一次；为了降低此过滤器的复杂度，默认前置过滤器已缓存了RequestBody
            String cachedBody = exchange.getAttribute(Constants.CACHED_NEW_REQUESTBODY);
            if (cachedBody == null) {
                // 该缓存由‘ReadBodyRoutePredicateFactory’写入
                cachedBody = exchange.getAttribute(Constants.CACHED_REQUESTBODY);
            }
            JSONObject json = JSON.parseObject(cachedBody);
            String token = json.getString("token");
            String accountId = json.getString("account");
            // TODO: token验证

            //{
            //    // token验证失败
            //    BaseResp resp = new BaseResp(ResultCodEnum.TOKEN_CHECK_FAILED);
            //    return response.writeWith(Mono.just(response.bufferFactory().wrap(JSON.toJSONString(resp).getBytes())));
            //
            //}

            return chain.filter(exchange);
        };
    }

    protected static class Config {

    }

}
