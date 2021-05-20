package cc.laop.feign.controller;

import cc.laop.feign.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: peng
 * @Date: create in 2021/5/19 17:38
 * @Description:
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Resource
    private UserService userService;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 以OpenFeign作为http客户端调用http接口
     *
     * @param username
     * @param password
     * @return
     */
    @PostMapping("login")
    public String login(String username, String password) {
        return userService.login(username, password);
    }

    /**
     * 使用RestTemplate调用http接口
     *
     * @param username
     * @param password
     * @return
     */
    @PostMapping("login2")
    public String login2(String username, String password) {
        Map map = new HashMap();
        map.put("username", username);
        map.put("password", password);
        ResponseEntity<String> resp = restTemplate.postForEntity("http://feign-provider/user/login",
                map, String.class);
        return resp.getBody();
    }

}
