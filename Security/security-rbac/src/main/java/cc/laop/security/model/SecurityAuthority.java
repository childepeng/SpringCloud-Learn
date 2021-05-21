package cc.laop.security.model;

import org.springframework.security.core.GrantedAuthority;

/**
 * @Auther: peng
 * @Date: create in 2021/5/21 16:54
 * @Description:
 */
public class SecurityAuthority implements GrantedAuthority {

    private String perms;

    public SecurityAuthority(String perms) {
        this.perms = perms;
    }

    @Override
    public String getAuthority() {
        return perms;
    }
}
