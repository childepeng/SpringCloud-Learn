package cc.laop.feign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Auther: peng
 * @Date: create in 2021/5/19 17:31
 * @Description:
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class FeignConsumerStarter {

    public static void main(String[] args) {
        SpringApplication.run(FeignConsumerStarter.class, args);
    }
}
