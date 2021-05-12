package cc.laop.data.es.controller;

import cc.laop.data.es.dataobject.ProgrammeQueryDTO;
import cc.laop.data.es.service.ProgrammeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Auther: peng
 * @Date: create in 2021/5/10 16:49
 * @Description:
 */
@RestController
@RequestMapping("programme")
public class ProgrammeController {

    @Autowired
    private ProgrammeService programmeService;

    @RequestMapping("queryByName")
    public List queryByName(String name) {
        return programmeService.queryByName(name, 0, 10);
    }


    @RequestMapping("search")
    public List query(@RequestBody ProgrammeQueryDTO dto) {
        return programmeService.compoundQuery(dto);
    }

    @RequestMapping("timeCount")
    public List createTimeCount() {
        return programmeService.createtimeHistogram();
    }


}
