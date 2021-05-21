package cc.laop.security.config.handler;

import cc.laop.security.model.vo.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

import static cc.laop.security.config.SecurityConstants.DEFAILT_CONTENT_TYPE;

/**
 * @Auther: peng
 * @Date: create in 2021/5/21 11:13
 * @Description:
 */
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                Authentication authentication) throws IOException, ServletException {
        try (OutputStream os = httpServletResponse.getOutputStream()) {
            Result result = new Result();
            result.setMessage("登出成功！");
            httpServletResponse.setContentType(DEFAILT_CONTENT_TYPE);
            os.write(new ObjectMapper().writeValueAsBytes(result));
        }
    }
}
