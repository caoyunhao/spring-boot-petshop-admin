package com.caoyunhao.petshop_admin.entity;

import javax.persistence.*;

/**
 * @author Yunhao.Cao
 * @version 1.0 2018/4/10
 */
@Entity
@Table(name = "commodity_image", schema = "petshop", catalog = "")
@IdClass(CommodityImagePK.class)
public class CommodityImage {
    private long commodityId;
    private long imageId;
    private int imageNumber;

    @Id
    @Column(name = "commodity_id", nullable = false)
    public long getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(long commodityId) {
        this.commodityId = commodityId;
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

        CommodityImage that = (CommodityImage) o;

        if (commodityId != that.commodityId) return false;
        if (imageId != that.imageId) return false;
        if (imageNumber != that.imageNumber) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (commodityId ^ (commodityId >>> 32));
        result = 31 * result + (int) (imageId ^ (imageId >>> 32));
        result = 31 * result + imageNumber;
        return result;
    }
}
