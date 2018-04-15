package com.caoyunhao.petshop_admin.module.commodity;

import com.caoyunhao.petshop_admin.common.page.PageParams;

/**
 * @author Yunhao.Cao
 * @version 1.0 2018/4/8
 */
public class CommoditySearchParams extends PageParams {
    private String q;

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }
}
