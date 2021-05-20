package cc.laop.security.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: peng
 * @Date: create in 2021/5/20 14:40
 * @Description:
 */
@RestController
public class UserController {

    @PostMapping("login")
    public String login(String username, String password) {
        return "success";
    }
    
    @PostMapping("logout")
    public String logout() {
        return "success";
    }

}
