package cc.laop.gateway.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: peng
 * @Date: create in 2021/5/19 14:37
 * @Description:
 */
public class JwtUtils {

    private int jwtTtl;

    private String secret;

    private JwtUtils(String secret, int ttl) {
        this.jwtTtl = ttl;
        this.secret = secret;
    }

    public static JwtUtils create(String secret, int ttl) {
        return new JwtUtils(secret, ttl);
    }

    /**
     * 生产token
     *
     * @param uid   用户ID
     * @param uname 用户名
     * @return
     */
    public String createToken(String uid, String uname) {
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.MINUTE, jwtTtl);
        Date expireDate = nowTime.getTime();

        Map<String, Object> header = new HashMap<>();
        // 算法
        header.put("alg", "HS384");
        // 类型
        header.put("typ", "JWT");
        return JWT.create()
                .withHeader(header)
                .withClaim("uid", uid)
                .withClaim("uname", uname)
                .withIssuedAt(new Date())
                .withExpiresAt(expireDate)
                .sign(Algorithm.HMAC256(secret));
    }


    /**
     * Token验证
     *
     * @param jwtToken
     * @return
     * @throws AlgorithmMismatchException     if the algorithm stated in the token's header it's not equal to the one
     *                                        defined in the {@link JWTVerifier}.
     * @throws SignatureVerificationException if the signature is invalid.
     * @throws TokenExpiredException          if the token has expired.
     * @throws InvalidClaimException          if a claim contained a different value than the expected one.
     */
    public Map<String, Claim> checkToken(String jwtToken) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret)).build();
        DecodedJWT verify = verifier.verify(jwtToken);
        return verify.getClaims();
    }


}
