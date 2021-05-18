package cc.laop.dubbo.web;

import cc.laop.dubbo.api.EsProgrammeService;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @Auther: peng
 * @Date: create in 2021/5/17 14:57
 * @Description:
 */
// @RestController
// @RequestMapping("/programme")
public class EsProgrammeController implements EsProgrammeService {

    @PostMapping("search")
    @Override
    public String search(String params) {
        return params;
    }
}
