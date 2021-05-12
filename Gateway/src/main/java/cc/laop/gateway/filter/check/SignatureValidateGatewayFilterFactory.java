package cc.laop.gateway.filter.check;

import cc.laop.gateway.dto.BaseResp;
import cc.laop.gateway.dto.ResultCode;
import cc.laop.gateway.util.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import reactor.core.publisher.Mono;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

/**
 * @Auther: peng
 * @Date: create in 2021/5/12 10:15
 * @Description: 请求签名验证
 */
@Component
public class SignatureValidateGatewayFilterFactory extends AbstractGatewayFilterFactory<SignatureValidateGatewayFilterFactory.Config> {

    public SignatureValidateGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public List<String> shortcutFieldOrder() {
        // 设置可配置的属性名称
        return Arrays.asList();
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();
            String token = request.getHeaders().getFirst("Token");
            String timeStr = request.getHeaders().getFirst("Time");
            String clientId = request.getHeaders().getFirst("Client-ID");

            if (StringUtils.isAnyEmpty(token, timeStr, clientId) || !org.apache.commons.lang3.StringUtils.isNumeric(timeStr)) {
                // 参数异常
                BaseResp resp = new BaseResp(ResultCode.ILLEGAL_ARGUMENTS);
                return response.writeWith(Mono.just(response.bufferFactory().wrap(resp.toString().getBytes())));
            }

            long clientTime = Long.valueOf(timeStr);
            long diff = System.currentTimeMillis() / 1000 - clientTime;
            if (diff > 30 || diff < -30) {
                // 签名过期
                BaseResp resp = new BaseResp(ResultCode.SIGN_EXPIRED);
                return response.writeWith(Mono.just(response.bufferFactory().wrap(resp.toString().getBytes())));
            }

            String url = request.getPath().toString();
            String key = "123";
            String originalSign = timeStr + clientId + url;
            try {
                String algorithm = "HmacSHA256";
                Mac mac = Mac.getInstance(algorithm);
                mac.init(new SecretKeySpec(key.getBytes(Charset.forName("UTF-8")), algorithm));
                byte[] signData = mac.doFinal(originalSign.getBytes(Charset.forName("UTF-8")));
                String newToken = Base64Utils.encodeToString(signData);
                if (!token.equals(newToken)) {
                    // 签名验证失败
                    BaseResp resp = new BaseResp(ResultCode.SIGN_CHECK_FAILED);
                    return response.writeWith(Mono.just(response.bufferFactory().wrap(resp.toString().getBytes())));
                }
            } catch (NoSuchAlgorithmException | InvalidKeyException e) {
                e.printStackTrace();
            }

            return chain.filter(exchange);
        };
    }

    protected static class Config {

    }

}
