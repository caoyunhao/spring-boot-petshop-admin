package com.caoyunhao.petshop_admin.module.role;

import java.util.List;

import com.caoyunhao.petshop_admin.entity.Auth;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.GeneratedValue;

/**
 * @author Yunhao.Cao
 * @version 1.0 2018/4/8
 */

public class RoleForm {
    private Long id;
    @NotBlank(message = "{rolename.blank}")
    private String roleName;
    private String roleDescription;
    private List<Auth> authList;

    public List<Auth> getAuthList() {
        return authList;
    }

    public void setAuthList(List<Auth> authList) {
        this.authList = authList;
    }

    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public interface Create {
    }

    public interface Update {
    }
}
