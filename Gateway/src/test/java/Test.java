import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.util.Base64Utils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @Auther: peng
 * @Date: create in 2021/5/11 15:03
 * @Description:
 */
public class Test {

    public static void main(String[] args) throws IOException, InvalidKeyException, NoSuchAlgorithmException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://localhost:8081/es/programme/search");
        httpPost.setEntity(new StringEntity("eyJxdWVyeSI6eyJtYXRjaCI6eyJuYW1lIjoidGhvciJ9fX0=",
                ContentType.APPLICATION_OCTET_STREAM));
        addToken(httpPost);
        CloseableHttpResponse response = httpClient.execute(httpPost);
        String rt = EntityUtils.toString(response.getEntity());
        String dert = new String(Base64Utils.decode(rt.getBytes()));
        System.out.println(dert);
    }

    public static void addToken(HttpPost post) throws NoSuchAlgorithmException, InvalidKeyException {
        String time = String.valueOf(System.currentTimeMillis() / 1000);
        String clientId = "123";
        String url = post.getURI().getPath();
        post.addHeader("time", time);
        post.addHeader("Client-ID", clientId);

        String key = "123";
        String originalSign = time + clientId + url;
        String algorithm = "HmacSHA256";
        Mac mac = null;
        mac = Mac.getInstance(algorithm);
        mac.init(new SecretKeySpec(key.getBytes(Charset.forName("UTF-8")), algorithm));
        byte[] signData = mac.doFinal(originalSign.getBytes(Charset.forName("UTF-8")));
        String token = Base64Utils.encodeToString(signData);
        post.addHeader("Token", token);
    }


}
