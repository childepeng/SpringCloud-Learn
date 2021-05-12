package cc.laop.gateway.filter.rewrite;

import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import java.nio.charset.Charset;

/**
 * @Auther: peng
 * @Date: create in 2021/5/11 11:29
 * @Description:
 */
@Component
public class Base64RewriteHandler implements RewriteHandler {

    Charset charset = Charset.forName("UTF-8");

    @Override
    public String encode(String content) {
        return new String(Base64Utils.encode(content.getBytes()), charset);
    }

    @Override
    public String decode(String content) {
        return new String(Base64Utils.decode(content.getBytes()), charset);
    }
}
