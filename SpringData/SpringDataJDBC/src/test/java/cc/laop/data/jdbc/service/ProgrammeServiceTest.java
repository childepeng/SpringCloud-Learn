package cc.laop.data.jdbc.service;

import cc.laop.data.jdbc.dataobject.ProgrammeDO;
import cc.laop.data.jdbc.repo.ProgrammeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @Auther: peng
 * @Date: create in 2021/5/10 18:25
 * @Description:
 */
@SpringBootTest
public class ProgrammeServiceTest {

    @Autowired
    ProgrammeRepository programmeRepository;

    @Test
    public void findByName() {
        String name = "thor";
        List<ProgrammeDO> list = programmeRepository.findByName(name);
        list.forEach(it -> System.out.println(it.toString()));
    }


    @Test
    public void findById() {
        System.out.println(programmeRepository.findById(27797).toString());
    }

}
