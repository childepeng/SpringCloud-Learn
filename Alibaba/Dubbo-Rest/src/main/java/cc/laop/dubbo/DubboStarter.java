package cc.laop.dubbo;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Auther: peng
 * @Date: create in 2021/5/13 10:34
 * @Description:
 */
@SpringBootApplication
@EnableDubbo
public class DubboStarter {

    public static void main(String[] args) {
        SpringApplication.run(DubboStarter.class, args);
    }

}
