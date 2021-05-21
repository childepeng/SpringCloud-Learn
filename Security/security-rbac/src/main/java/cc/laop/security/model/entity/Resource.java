package cc.laop.security.model.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

/**
 * @Auther: peng
 * @Date: create in 2021/5/20 17:30
 * @Description:
 */
@Table("t_resource")
public class Resource {

    @Id
    private int id;
    private String name;
    private String url;
    private String perms;

    public String getPerms() {
        return perms;
    }

    public Resource setPerms(String perms) {
        this.perms = perms;
        return this;
    }

    public int getId() {
        return id;
    }

    public Resource setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Resource setName(String name) {
        this.name = name;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public Resource setUrl(String url) {
        this.url = url;
        return this;
    }

}
