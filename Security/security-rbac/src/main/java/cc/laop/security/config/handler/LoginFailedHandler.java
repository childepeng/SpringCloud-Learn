package cc.laop.security.config.handler;

import cc.laop.security.constant.ResultCodeEnum;
import cc.laop.security.model.vo.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static cc.laop.security.config.SecurityConstants.DEFAILT_CONTENT_TYPE;

/**
 * @Auther: peng
 * @Date: create in 2021/5/21 10:49
 * @Description:
 */
public class LoginFailedHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        Result result = null;
        if (exception.fillInStackTrace().getClass() == LockedException.class) {
            result = Result.of(ResultCodeEnum.USER_LOCKED);
        }
        if (exception.fillInStackTrace().getClass() == DisabledException.class) {
            result = Result.of(ResultCodeEnum.USER_DISABLED);
        }
        if (exception.fillInStackTrace().getClass() == AccountExpiredException.class) {
            result = Result.of(ResultCodeEnum.USER_EXPIRED);
        }
        if (exception.fillInStackTrace().getClass() == CredentialsExpiredException.class) {
            result = Result.of(ResultCodeEnum.USER_CREDENTIAL_EXPIRED);
        }
        if (exception.fillInStackTrace().getClass() == InternalAuthenticationServiceException.class) {
            result = Result.of(ResultCodeEnum.USER_PASSWORD_WRONG);
        }
        if (exception.fillInStackTrace().getClass() == BadCredentialsException.class) {
            result = Result.of(ResultCodeEnum.USER_PASSWORD_WRONG);
        }
        if (result == null) {
            result = Result.of(ResultCodeEnum.UNKNOWN_ERROR);
        }
        response.setContentType(DEFAILT_CONTENT_TYPE);
        try (ServletOutputStream os = response.getOutputStream()) {
            os.write(new ObjectMapper().writeValueAsBytes(result));
        }
    }
}
