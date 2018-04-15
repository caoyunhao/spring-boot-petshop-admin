package com.caoyunhao.petshop_admin.module.custom;

import com.caoyunhao.petshop_admin.common.util.RegexValidatorUtil;
import com.caoyunhao.petshop_admin.entity.Role;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

public class CustomForm {
    @Pattern(regexp = RegexValidatorUtil.REGEX_USERNAME)
    private String customName;
    @NotNull
    private String customNameEncrypted;
    @Pattern(regexp = RegexValidatorUtil.REGEX_NICKNAME)
    private String customNickname;
    @Pattern(regexp = RegexValidatorUtil.REGEX_MOBILE)
    private String customPhoneNumber;
    @Email
    private String customEmail;

    private List<Role> roleList;

    public String getCustomName() {
        return customName;
    }

    public void setCustomName(String customName) {
        this.customName = customName;
    }

    public String getCustomNameEncrypted() {
        return customNameEncrypted;
    }

    public void setCustomNameEncrypted(String customNameEncrypted) {
        this.customNameEncrypted = customNameEncrypted;
    }

    public String getCustomNickname() {
        return customNickname;
    }

    public void setCustomNickname(String customNickname) {
        this.customNickname = customNickname;
    }

    public String getCustomPhoneNumber() {
        return customPhoneNumber;
    }

    public void setCustomPhoneNumber(String customPhoneNumber) {
        this.customPhoneNumber = customPhoneNumber;
    }

    public String getCustomEmail() {
        return customEmail;
    }

    public void setCustomEmail(String customEmail) {
        this.customEmail = customEmail;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    public interface Create {
    }

    public interface Update {
    }
}
