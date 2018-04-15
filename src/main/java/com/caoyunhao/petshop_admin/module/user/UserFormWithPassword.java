package com.caoyunhao.petshop_admin.module.user;

import org.hibernate.validator.constraints.NotBlank;

public class UserFormWithPassword extends UserForm {

    @NotBlank(message = "{password.blank}", groups = Create.class)
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
