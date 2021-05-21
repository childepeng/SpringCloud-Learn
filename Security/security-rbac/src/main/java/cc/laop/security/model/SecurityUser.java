package cc.laop.security.model;

import cc.laop.security.model.entity.User;
import cc.laop.security.util.BooleanUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

/**
 * @Auther: peng
 * @Date: create in 2021/5/21 16:51
 * @Description:
 */
public class SecurityUser implements UserDetails {

    private User user;
    private Set<SecurityAuthority> authoritys;

    public SecurityUser(User user, Set<SecurityAuthority> authoritys) {
        this.user = user;
        this.authoritys = authoritys;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authoritys;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !BooleanUtils.isTure(user.getLocked());
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.getEnable();
    }
}
