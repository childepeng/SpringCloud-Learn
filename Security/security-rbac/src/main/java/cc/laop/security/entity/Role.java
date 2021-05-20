package cc.laop.security.entity;

import org.springframework.data.relational.core.mapping.Table;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

/**
 * @Auther: peng
 * @Date: create in 2021/5/20 17:27
 * @Description:
 */
@Table("t_role")
public class Role implements GrantedAuthority {

    private int id;
    private String name;
    private String description;

    private List<Resource> resourceList;

    public List<Resource> getResourceList() {
        return resourceList;
    }

    public Role setResourceList(List<Resource> resourceList) {
        this.resourceList = resourceList;
        return this;
    }

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

    @Override
    public String getAuthority() {
        return this.name;
    }
}
