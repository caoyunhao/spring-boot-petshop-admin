package com.caoyunhao.petshop_admin.entity;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author Yunhao.Cao
 * @version 1.0 2018/4/10
 */
@Entity
public class User {
    private long id;
    private String userName;
    private String userNameEncrypted;
    private String password;
    private Timestamp updateTime;
    private Timestamp createTime;
    private Long userAvatar;
    private String userNickname;
    private String userPhoneNumber;
    private String userEmail;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "user_name", nullable = false, length = 100)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Basic
    @Column(name = "user_name_encrypted", nullable = false, length = 128)
    public String getUserNameEncrypted() {
        return userNameEncrypted;
    }

    public void setUserNameEncrypted(String userNameEncrypted) {
        this.userNameEncrypted = userNameEncrypted;
    }

    @Basic
    @Column(name = "password", nullable = false, length = 128)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "update_time", nullable = true)
    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    @Basic
    @Column(name = "create_time", nullable = true)
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "user_avatar", nullable = true)
    public Long getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(Long userAvatar) {
        this.userAvatar = userAvatar;
    }

    @Basic
    @Column(name = "user_nickname", nullable = true, length = 255)
    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    @Basic
    @Column(name = "user_phone_number", nullable = true, length = 20)
    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    @Basic
    @Column(name = "user_email", nullable = true, length = 30)
    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (userName != null ? !userName.equals(user.userName) : user.userName != null) return false;
        if (userNameEncrypted != null ? !userNameEncrypted.equals(user.userNameEncrypted) : user.userNameEncrypted != null)
            return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (updateTime != null ? !updateTime.equals(user.updateTime) : user.updateTime != null) return false;
        if (createTime != null ? !createTime.equals(user.createTime) : user.createTime != null) return false;
        if (userAvatar != null ? !userAvatar.equals(user.userAvatar) : user.userAvatar != null) return false;
        if (userNickname != null ? !userNickname.equals(user.userNickname) : user.userNickname != null) return false;
        if (userPhoneNumber != null ? !userPhoneNumber.equals(user.userPhoneNumber) : user.userPhoneNumber != null)
            return false;
        if (userEmail != null ? !userEmail.equals(user.userEmail) : user.userEmail != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (userNameEncrypted != null ? userNameEncrypted.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (userAvatar != null ? userAvatar.hashCode() : 0);
        result = 31 * result + (userNickname != null ? userNickname.hashCode() : 0);
        result = 31 * result + (userPhoneNumber != null ? userPhoneNumber.hashCode() : 0);
        result = 31 * result + (userEmail != null ? userEmail.hashCode() : 0);
        return result;
    }
}
