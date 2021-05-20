package cc.laop.security.entity;

import org.springframework.data.relational.core.mapping.Table;

/**
 * @Auther: peng
 * @Date: create in 2021/5/20 17:30
 * @Description:
 */
@Table("t_resource")
public class Resource {

    private int id;
    private String name;
    private String url;

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
