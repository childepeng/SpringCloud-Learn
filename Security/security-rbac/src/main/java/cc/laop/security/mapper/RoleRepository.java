package cc.laop.security.mapper;

import cc.laop.security.entity.Role;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @Auther: peng
 * @Date: create in 2021/5/20 17:26
 * @Description:
 */
public interface RoleRepository extends CrudRepository<Role, Integer> {

    @Query("select * from t_role where id in (select roleid from t_user_role where userid = :userid)")
    List<Role> findByUserid(@Param("userid") int userid);
    
}
