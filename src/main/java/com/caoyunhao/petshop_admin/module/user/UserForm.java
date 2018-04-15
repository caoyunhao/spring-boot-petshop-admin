package com.caoyunhao.petshop_admin.module.user;

import com.caoyunhao.petshop_admin.entity.Role;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import java.sql.Timestamp;
import java.util.List;

public class UserForm {

    @NotBlank(message = "{username.blank}", groups = Create.class)
    private String userName;
    @Pattern(regexp = "^[0-9]*$", message = "{userphonenumber.error}", groups = Create.class)
    private String userPhoneNumber;
    private List<Role> roleList;
    private Long id;
    private String userEmail;
    private Timestamp createTime;
    private Timestamp updateTime;
    private Long userAvatarId;
    private String userNickname;
    private String userNameEncrypted;

    public String getUserNameEncrypted() {
        return userNameEncrypted;
    }

    public void setUserNameEncrypted(String userNameEncrypted) {
        this.userNameEncrypted = userNameEncrypted;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    };

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public Long getUserAvatarId() {
        return userAvatarId;
    }

    public void setUserAvatarId(Long userAvatarId) {
        this.userAvatarId = userAvatarId;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public interface Create {
    }

    public interface Update {
    }
}
