package cc.laop.session.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @Auther: peng
 * @Date: create in 2021/5/24 11:13
 * @Description:
 */
@Configuration
@EnableRedisHttpSession
public class SpringSessionConfig {


    // @Bean
    // public LettuceConnectionFactory connectionFactory(RedisStandaloneConfiguration configuration) {
    //     return new LettuceConnectionFactory(configuration);
    // }


}
