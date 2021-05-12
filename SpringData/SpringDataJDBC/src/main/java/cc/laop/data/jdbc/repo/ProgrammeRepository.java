package cc.laop.data.jdbc.repo;

import cc.laop.data.jdbc.dataobject.ProgrammeDO;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @Auther: peng
 * @Date: create in 2021/5/10 18:20
 * @Description:
 */
public interface ProgrammeRepository extends CrudRepository<ProgrammeDO, Integer> {

    @Query("select * from b_programme where name like concat('%',:name,'%')")
    List<ProgrammeDO> findByName(@Param("name") String name);

    ProgrammeDO findById(int id);

    List<ProgrammeDO> findByImdbid(String imdbid);

}
