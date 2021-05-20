package cc.laop.gateway.filter.check;

import cc.laop.gateway.dto.BaseResp;
import cc.laop.gateway.dto.ResultCodeEnum;
import cc.laop.gateway.util.JwtUtils;
import com.alibaba.fastjson.JSON;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * @Auther: peng
 * @Date: create in 2021/5/19 15:21
 * @Description: Token有效性验证
 */
@Component
public class JwtValidateGatewayFilterFactory extends AbstractGatewayFilterFactory {

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.ttl}")
    private int ttl;

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();
            String token = request.getHeaders().getFirst("Authorization");
            BaseResp resp = null;
            try {
                Map<String, Claim> map = JwtUtils.create(secret, ttl).checkToken(token);
                // todo: other validate
            } catch (AlgorithmMismatchException e1) {
                // 算法不匹配
                resp = new BaseResp(ResultCodeEnum.TOKEN_ILLEGAL);
            } catch (SignatureVerificationException e2) {
                // token 不合法
                resp = new BaseResp(ResultCodeEnum.TOKEN_ILLEGAL);
            } catch (TokenExpiredException e3) {
                // token 过期
                resp = new BaseResp(ResultCodeEnum.TOKEN_EXPIRED);
            }
            if (resp == null || resp.getResult() == ResultCodeEnum.SUCCESS.getCode()) {
                return chain.filter(exchange);
            } else {
                return response.writeWith(Mono.just(response.bufferFactory().wrap(JSON.toJSONString(resp).getBytes())));
            }
        };
    }
}
