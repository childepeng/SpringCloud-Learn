package cc.laop.security.config.handler;

import cc.laop.security.model.SecurityUser;
import cc.laop.security.model.vo.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static cc.laop.security.config.SecurityConstants.DEFAILT_CONTENT_TYPE;

/**
 * @Auther: peng
 * @Date: create in 2021/5/21 10:39
 * @Description:
 */
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        SecurityUser user = (SecurityUser) authentication.getPrincipal();
        Result result = new Result();
        result.setData(user.getUsername());
        result.setMessage("登录成功");
        httpServletResponse.setContentType(DEFAILT_CONTENT_TYPE);
        try (ServletOutputStream os = httpServletResponse.getOutputStream()) {
            os.write(new ObjectMapper().writeValueAsBytes(result));
        }
    }
}
