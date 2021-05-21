package cc.laop.security.config.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Auther: peng
 * @Date: create in 2021/5/21 15:38
 * @Description:
 */
public class JsonUserAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public JsonUserAuthenticationFilter() {
        super();
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String contentType = request.getContentType();
        if (contentType != null && contentType.startsWith(MediaType.APPLICATION_JSON_VALUE)) {
            ObjectMapper mapper = new ObjectMapper();
            UsernamePasswordAuthenticationToken authRequest = null;
            try (ServletInputStream inputStream = request.getInputStream()) {
                final Map map = mapper.readValue(inputStream, Map.class);
                authRequest = new UsernamePasswordAuthenticationToken(map.get(getUsernameParameter()),
                        map.get(getPasswordParameter()));
            } catch (Exception e) {
                e.printStackTrace();
                authRequest = new UsernamePasswordAuthenticationToken(
                        "", "");
            }
            setDetails(request, authRequest);
            return getAuthenticationManager().authenticate(authRequest);
        } else {
            return super.attemptAuthentication(request, response);
        }
    }
}
