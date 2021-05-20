package cc.laop.feign.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: peng
 * @Date: create in 2021/5/20 9:32
 * @Description:
 */
@RestController
public class UserController {

    @PostMapping("user/login")
    public String login(String username, String password) {
        return "hello";
    }

}
