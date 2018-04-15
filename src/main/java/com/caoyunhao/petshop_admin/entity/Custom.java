package com.caoyunhao.petshop_admin.entity;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author Yunhao.Cao
 * @version 1.0 2018/4/10
 */
@Entity
public class Custom {
    private long id;
    private String customName;
    private String customNameEncrypted;
    private String password;
    private String customNickname;
    private String customPhoneNumber;
    private String customEmail;
    private Timestamp updateTime;
    private Timestamp createTime;

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
    @Column(name = "custom_name", nullable = false, length = 100)
    public String getCustomName() {
        return customName;
    }

    public void setCustomName(String customName) {
        this.customName = customName;
    }

    @Basic
    @Column(name = "custom_name_encrypted", nullable = false, length = 128)
    public String getCustomNameEncrypted() {
        return customNameEncrypted;
    }

    public void setCustomNameEncrypted(String customNameEncrypted) {
        this.customNameEncrypted = customNameEncrypted;
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
    @Column(name = "custom_nickname", nullable = true, length = 100)
    public String getCustomNickname() {
        return customNickname;
    }

    public void setCustomNickname(String customNickname) {
        this.customNickname = customNickname;
    }

    @Basic
    @Column(name = "custom_phone_number", nullable = true, length = 20)
    public String getCustomPhoneNumber() {
        return customPhoneNumber;
    }

    public void setCustomPhoneNumber(String customPhoneNumber) {
        this.customPhoneNumber = customPhoneNumber;
    }

    @Basic
    @Column(name = "custom_email", nullable = true, length = 30)
    public String getCustomEmail() {
        return customEmail;
    }

    public void setCustomEmail(String customEmail) {
        this.customEmail = customEmail;
    }

    @Basic
    @Column(name = "update_time", nullable = false)
    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    @Basic
    @Column(name = "create_time", nullable = false)
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Custom custom = (Custom) o;

        if (id != custom.id) return false;
        if (customName != null ? !customName.equals(custom.customName) : custom.customName != null) return false;
        if (customNameEncrypted != null ? !customNameEncrypted.equals(custom.customNameEncrypted) : custom.customNameEncrypted != null)
            return false;
        if (password != null ? !password.equals(custom.password) : custom.password != null) return false;
        if (customNickname != null ? !customNickname.equals(custom.customNickname) : custom.customNickname != null)
            return false;
        if (customPhoneNumber != null ? !customPhoneNumber.equals(custom.customPhoneNumber) : custom.customPhoneNumber != null)
            return false;
        if (customEmail != null ? !customEmail.equals(custom.customEmail) : custom.customEmail != null) return false;
        if (updateTime != null ? !updateTime.equals(custom.updateTime) : custom.updateTime != null) return false;
        if (createTime != null ? !createTime.equals(custom.createTime) : custom.createTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (customName != null ? customName.hashCode() : 0);
        result = 31 * result + (customNameEncrypted != null ? customNameEncrypted.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (customNickname != null ? customNickname.hashCode() : 0);
        result = 31 * result + (customPhoneNumber != null ? customPhoneNumber.hashCode() : 0);
        result = 31 * result + (customEmail != null ? customEmail.hashCode() : 0);
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        return result;
    }
}
