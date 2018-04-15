package com.caoyunhao.petshop_admin.entity;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author Yunhao.Cao
 * @version 1.0 2018/4/10
 */
@Entity
@Table(name = "user_nonce", schema = "petshop", catalog = "")
public class UserNonce {
    private long userId;
    private String nonce;
    private Timestamp overdueTime;

    @Id
    @Column(name = "user_id", nullable = false)
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "nonce", nullable = false, length = 255)
    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    @Basic
    @Column(name = "overdue_time", nullable = false)
    public Timestamp getOverdueTime() {
        return overdueTime;
    }

    public void setOverdueTime(Timestamp overdueTime) {
        this.overdueTime = overdueTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserNonce userNonce = (UserNonce) o;

        if (userId != userNonce.userId) return false;
        if (nonce != null ? !nonce.equals(userNonce.nonce) : userNonce.nonce != null) return false;
        if (overdueTime != null ? !overdueTime.equals(userNonce.overdueTime) : userNonce.overdueTime != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (userId ^ (userId >>> 32));
        result = 31 * result + (nonce != null ? nonce.hashCode() : 0);
        result = 31 * result + (overdueTime != null ? overdueTime.hashCode() : 0);
        return result;
    }
}
