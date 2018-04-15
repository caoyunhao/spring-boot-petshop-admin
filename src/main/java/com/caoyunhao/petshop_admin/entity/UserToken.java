package com.caoyunhao.petshop_admin.entity;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author Yunhao.Cao
 * @version 1.0 2018/4/10
 */
@Entity
@Table(name = "user_token", schema = "petshop", catalog = "")
public class UserToken {
    private long userId;
    private String userToken;
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
    @Column(name = "user_token", nullable = false, length = 128)
    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
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

        UserToken userToken1 = (UserToken) o;

        if (userId != userToken1.userId) return false;
        if (userToken != null ? !userToken.equals(userToken1.userToken) : userToken1.userToken != null) return false;
        if (overdueTime != null ? !overdueTime.equals(userToken1.overdueTime) : userToken1.overdueTime != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (userId ^ (userId >>> 32));
        result = 31 * result + (userToken != null ? userToken.hashCode() : 0);
        result = 31 * result + (overdueTime != null ? overdueTime.hashCode() : 0);
        return result;
    }
}
