package cc.laop.session.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @Auther: peng
 * @Date: create in 2021/5/24 11:10
 * @Description:
 */
@RestController
public class TestController {

    @RequestMapping("test")
    public String hello(HttpSession session) {
        session.setAttribute("test", "hello");
        return "hello: " + session.getId();
    }


}
