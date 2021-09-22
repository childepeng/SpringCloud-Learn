package cc.laop.data.es.service;

import cc.laop.data.es.dataobject.ProgrammeDO;
import cc.laop.data.es.repo.ProgrammeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: peng
 * @Date: create in 2021/5/10 16:05
 * @Description:
 */
@Service
public class ProgrammeService {
    @Autowired
    private ProgrammeRepository programmeRepository;

    public void save(ProgrammeDO programmeDO) {
        programmeRepository.save(programmeDO);
    }

    /**
     * @param name
     * @param pageNum  起始页为 0
     * @param pageSize
     * @return
     */
    public List<ProgrammeDO> queryByName(String name, int pageNum, int pageSize) {
        return programmeRepository.findByName(name, PageRequest.of(pageNum, pageSize));
    }


}
