package cc.laop.security.config;

import cc.laop.security.mapper.ResourceRepository;
import cc.laop.security.mapper.RoleRepository;
import cc.laop.security.mapper.UserRepository;
import cc.laop.security.model.entity.Resource;
import cc.laop.security.model.entity.User;
import cc.laop.security.util.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Auther: peng
 * @Date: create in 2021/5/20 18:05
 * @Description:
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ResourceRepository resourceRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户名或者密码错误");
        }
        List<Resource> reslist = resourceRepository.findByUserid(user.getId());
        // return user.setResourceList(reslist);

        Set<SimpleGrantedAuthority> authoritys =
                reslist.stream().map(it -> new SimpleGrantedAuthority(it.getPerms())).collect(Collectors.toSet());

        org.springframework.security.core.userdetails.User securityUser =
                new org.springframework.security.core.userdetails.User(
                        user.getUsername(),
                        user.getPassword(),
                        BooleanUtils.isTure(user.getEnable()),
                        true,
                        true,
                        !BooleanUtils.isTure(user.getLocked()),
                        authoritys);

        return securityUser;
    }
}
