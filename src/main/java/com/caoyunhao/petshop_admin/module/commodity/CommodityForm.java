package com.caoyunhao.petshop_admin.module.commodity;

import com.caoyunhao.petshop_admin.common.util.RegexValidatorUtil;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author Yunhao.Cao
 * @version 1.0 2018/4/8
 */
public class CommodityForm {
    @NotBlank(message = "商品名不能为空")
    @Pattern(regexp = RegexValidatorUtil.REGEX_COMMODITY_NAME)
    private String commodityName;
    @Min(value = 0)
    private double commodityPrice;
    @Size(max = 140)
    private String commodityDescription;
    @Min(value = 0)
    private long commodityLast;
    @Min(value = 0)
    private long commoditySold;
    private List<Long> commodityCategoryIdList;

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public double getCommodityPrice() {
        return commodityPrice;
    }

    public void setCommodityPrice(double commodityPrice) {
        this.commodityPrice = commodityPrice;
    }

    public String getCommodityDescription() {
        return commodityDescription;
    }

    public void setCommodityDescription(String commodityDescription) {
        this.commodityDescription = commodityDescription;
    }

    public long getCommodityLast() {
        return commodityLast;
    }

    public void setCommodityLast(long commodityLast) {
        this.commodityLast = commodityLast;
    }

    public long getCommoditySold() {
        return commoditySold;
    }

    public void setCommoditySold(long commoditySold) {
        this.commoditySold = commoditySold;
    }

    public List<Long> getCommodityCategoryIdList() {
        return commodityCategoryIdList;
    }

    public void setCommodityCategoryIdList(List<Long> commodityCategoryIdList) {
        this.commodityCategoryIdList = commodityCategoryIdList;
    }
}
