package com.caoyunhao.petshop_admin.entity;

import javax.persistence.*;

/**
 * @author Yunhao.Cao
 * @version 1.0 2018/4/10
 */
@Entity
@Table(name = "user_image", schema = "petshop", catalog = "")
@IdClass(UserImagePK.class)
public class UserImage {
    private long userId;
    private long imageId;
    private int imageNumber;

    @Id
    @Column(name = "user_id", nullable = false)
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "image_id", nullable = false)
    public long getImageId() {
        return imageId;
    }

    public void setImageId(long imageId) {
        this.imageId = imageId;
    }

    @Id
    @Column(name = "image_number", nullable = false)
    public int getImageNumber() {
        return imageNumber;
    }

    public void setImageNumber(int imageNumber) {
        this.imageNumber = imageNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserImage userImage = (UserImage) o;

        if (userId != userImage.userId) return false;
        if (imageId != userImage.imageId) return false;
        if (imageNumber != userImage.imageNumber) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (userId ^ (userId >>> 32));
        result = 31 * result + (int) (imageId ^ (imageId >>> 32));
        result = 31 * result + imageNumber;
        return result;
    }
}
