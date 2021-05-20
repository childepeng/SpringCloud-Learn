package cc.laop.feign.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Auther: peng
 * @Date: create in 2021/5/20 9:52
 * @Description:
 */
@FeignClient(name = "feign-provider")
public interface UserService {

    @PostMapping("user/login")
    String login(@RequestParam("username") String username, @RequestParam("password") String password);

}
