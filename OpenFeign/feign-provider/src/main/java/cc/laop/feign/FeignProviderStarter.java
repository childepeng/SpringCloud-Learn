package cc.laop.feign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Auther: peng
 * @Date: create in 2021/5/19 17:30
 * @Description:
 */
@SpringBootApplication
@EnableDiscoveryClient
public class FeignProviderStarter {

    public static void main(String[] args) {
        SpringApplication.run(FeignProviderStarter.class, args);
    }

}
