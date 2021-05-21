package cc.laop.security.mapper;

import cc.laop.security.model.entity.User;
import org.springframework.data.repository.CrudRepository;

/**
 * @Auther: peng
 * @Date: create in 2021/5/20 17:25
 * @Description:
 */
public interface UserRepository extends CrudRepository<User, Integer> {

    User findByUsernameAndPassword(String username, String password);

    User findByUsername(String username);

}
