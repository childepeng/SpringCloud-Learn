package cc.laop.security.model.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

/**
 * @Auther: peng
 * @Date: create in 2021/5/20 17:27
 * @Description:
 */
@Table("t_role")
public class Role {

    @Id
    private int id;
    private String name;
    private String description;

    public int getId() {
        return id;
    }

    public Role setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Role setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Role setDescription(String description) {
        this.description = description;
        return this;
    }

}
