package cc.laop.security.config;

import cc.laop.security.config.filter.JsonUserAuthenticationFilter;
import cc.laop.security.config.handler.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.session.security.web.authentication.SpringSessionRememberMeServices;

/**
 * @Auther: peng
 * @Date: create in 2021/5/20 17:38
 * @Description: Security配置。 {@link EnableGlobalMethodSecurity} 启用方法访问权限控制
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
        super.configure(auth);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // super.configure(http);
        // http.requestCache().disable()
        //         // csrf 跨站请求伪造，默认情况下会拦截所有 PATCH/POST/DELETE/PUT 请求，需要添加请求头csrfToken
        //         .csrf().disable()
        //         .authorizeRequests()
        //         .antMatchers("/test1", "/test2").authenticated()
        //         .antMatchers("/**").permitAll()
        //         .and()
        //         .formLogin().loginPage("/login").permitAll()
        //         .and()
        //         .logout().logoutUrl("/logout").permitAll()
        //         .logoutSuccessHandler(new LogoutSuccessHandlerImpl())
        //         .and()
        //         // .authorizeRequests().anyRequest().permitAll()
        //         // .and()
        //         .rememberMe().disable()
        //         .exceptionHandling()
        //         .accessDeniedHandler(new AccessDeniedHandlerImpl())
        //         .authenticationEntryPoint(new AuthenticationEntryPointImpl());

        http.addFilterAt(jsonUserAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        http.requestCache().disable()
                .csrf().disable()
                .authorizeRequests(expressionInterceptUrlRegistry -> expressionInterceptUrlRegistry
                        .antMatchers("/**").permitAll())
                // 登录配置， 上面已经添加JsonUserAuthenticationFilter, 与下面配置重复了，以下配置并不生效，所以可以移除
                // .formLogin(httpSecurityFormLoginConfigurer -> httpSecurityFormLoginConfigurer
                //         .loginPage("/login").permitAll()
                //         .successHandler(new LoginSuccessHandler())
                //         .failureHandler(new LoginFailedHandler())
                //         .loginProcessingUrl("/login"))
                // 登出配置
                .logout(httpSecurityLogoutConfigurer -> httpSecurityLogoutConfigurer
                        .logoutUrl("/logout").permitAll()
                        .logoutSuccessHandler(new LogoutSuccessHandlerImpl()))
                // .rememberMe().disable()
                .rememberMe(httpSecurityRememberMeConfigurer ->
                        httpSecurityRememberMeConfigurer.rememberMeServices(rememberMeServices()))
                // 异常处理
                .exceptionHandling(httpSecurityExceptionHandlingConfigurer -> httpSecurityExceptionHandlingConfigurer
                        .accessDeniedHandler(new AccessDeniedHandlerImpl())
                        .authenticationEntryPoint(new AuthenticationEntryPointImpl()));
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


    @Bean
    public JsonUserAuthenticationFilter jsonUserAuthenticationFilter() throws Exception {
        JsonUserAuthenticationFilter filter = new JsonUserAuthenticationFilter();
        filter.setAuthenticationManager(super.authenticationManagerBean());
        filter.setAuthenticationFailureHandler(new LoginFailedHandler());
        filter.setAuthenticationSuccessHandler(new LoginSuccessHandler());
        return filter;
    }


    @Bean
    public RememberMeServices rememberMeServices() {
        SpringSessionRememberMeServices rememberMeServices = new SpringSessionRememberMeServices();
        rememberMeServices.setValiditySeconds(120);
        return rememberMeServices;
    }


    /**
     * Spring Session Redis 默认序列化方式
     *
     * @return
     */
    @Bean("springSessionDefaultRedisSerializer")
    public RedisSerializer redisSerializer() {
        return new GenericJackson2JsonRedisSerializer();
    }

}
