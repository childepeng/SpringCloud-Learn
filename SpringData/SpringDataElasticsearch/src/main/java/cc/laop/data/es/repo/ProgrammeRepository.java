package cc.laop.data.es.repo;

import cc.laop.data.es.dataobject.ProgrammeDO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * @Auther: Administrator
 * @Date: create in 2021/5/10 16:02
 * @Description:
 */
public interface ProgrammeRepository extends ElasticsearchRepository<ProgrammeDO, Integer> {

    List<ProgrammeDO> findByImdbScoreBetween(String start, String end);
    

    List<ProgrammeDO> findByName(String name, Pageable pageable);

    /**
     * 根据权限类型与分组查询节目
     *
     * @param serviceTypeId
     * @param columnId
     * @param pageable
     * @return
     */
    List<ProgrammeDO> findByServiceTypeIdsAndColumnIds(Integer serviceTypeId, Integer columnId, Pageable pageable);

}
