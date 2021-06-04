package cc.laop.mq.config;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther: peng
 * @Date: create in 2021/5/26 18:08
 * @Description:
 */
@Configuration
public class RocketMQConfig {


    @Bean
    public RocketMQTemplate rocketMQTemplate(RocketMQTemplate rocketMQTemplate) {
        return rocketMQTemplate;
    }

}
