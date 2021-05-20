package cc.laop.security.mapper;

import cc.laop.security.entity.Resource;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @Auther: peng
 * @Date: create in 2021/5/20 17:26
 * @Description:
 */
public interface ResourceRepository extends CrudRepository<Resource, Integer> {

    @Query("select * from t_resource where id in (select resourceid from t_role_resource where roleid = :roleid)")
    public List<Resource> findByRoleid(@Param("roleid") int roleid);

}
