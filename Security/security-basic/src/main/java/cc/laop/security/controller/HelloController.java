package cc.laop.security.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: peng
 * @Date: create in 2021/5/20 11:33
 * @Description:
 */
@RestController
public class HelloController {

    @RequestMapping("test1")
    public String test1() {
        return "hello test1!";
    }

    @RequestMapping("test2")
    public String test2() {
        return "hello test2!";
    }

    @RequestMapping("test3")
    public String test3() {
        return "hello test3!";
    }

}
