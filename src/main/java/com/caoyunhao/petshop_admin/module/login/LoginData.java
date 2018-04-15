package com.caoyunhao.petshop_admin.module.login;

/**
 * @author Yunhao.Cao
 * @version 1.0 2018/4/8
 */
public class LoginData {
    private String userId;
    private String userToken;
    private String userName;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
