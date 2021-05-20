package cc.laop.feign.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

/**
 * @Auther: peng
 * @Date: create in 2021/5/20 10:08
 * @Description:
 */
@Configuration
public class RestTemplateConfig {

    /**
     * 初始化RestTemplate客户端
     * {@link LoadBalanced} 注解表示针对RestTemplate和WebClient为负载均衡模式
     *
     * @param restTemplateBuilder
     * @return
     */
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.setConnectTimeout(Duration.ofSeconds(30))
                .setReadTimeout(Duration.ofSeconds(10))
                .build();
    }

}
