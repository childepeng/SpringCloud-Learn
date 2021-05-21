package cc.laop.security.config.handler;

import cc.laop.security.constant.ResultCodeEnum;
import cc.laop.security.model.vo.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

import static cc.laop.security.config.SecurityConstants.DEFAILT_CONTENT_TYPE;

/**
 * @Auther: peng
 * @Date: create in 2021/5/21 11:25
 * @Description: 匿名用户访问未授权资源
 */
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                         AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setContentType(DEFAILT_CONTENT_TYPE);
        try (OutputStream os = httpServletResponse.getOutputStream()) {
            os.write(new ObjectMapper().writeValueAsBytes(Result.of(ResultCodeEnum.USER_NO_LOGIN)));
        }
    }
}
