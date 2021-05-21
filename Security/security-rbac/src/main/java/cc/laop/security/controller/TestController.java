package cc.laop.security.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: peng
 * @Date: create in 2021/5/21 13:47
 * @Description:
 */
@RestController
public class TestController {

    @PostMapping("test1")
    @PreAuthorize("hasAuthority('test:1')")
    public String test1() {
        return "test1";
    }

    @PostMapping("test2")
    @PreAuthorize("hasAuthority('test:2')")
    public String test2() {
        return "test2";
    }

    @RequestMapping("test3")
    public String test3() {
        return "test3";
    }

    @RequestMapping("test4")
    public String test4() {
        return "test4";
    }


}
