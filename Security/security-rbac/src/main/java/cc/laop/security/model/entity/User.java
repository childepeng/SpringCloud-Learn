package cc.laop.security.model.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Date;

/**
 * @Auther: peng
 * @Date: create in 2021/5/20 15:14
 * @Description:
 */
@Table("t_user")
public class User {

    @Id
    private int id;
    private String username;
    private String password;
    private Boolean locked;
    private Boolean enable;
    @Column("create_time")
    private Date createTime;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Boolean getLocked() {
        return locked;
    }

    public User setLocked(Boolean locked) {
        this.locked = locked;
        return this;
    }

    public Boolean getEnable() {
        return enable;
    }

    public User setEnable(Boolean enable) {
        this.enable = enable;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public User setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public int getId() {
        return id;
    }

    public User setId(int id) {
        this.id = id;
        return this;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

}
