package com.caoyunhao.petshop_admin.module.commodity;

import com.caoyunhao.petshop_admin.common.BaseRequest;
import com.caoyunhao.petshop_admin.common.page.PageParams;
import com.caoyunhao.petshop_admin.common.response.BaseResponse;
import com.caoyunhao.petshop_admin.common.response.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @author Yunhao.Cao
 * @version 1.0 2018/4/8
 */
@RequestMapping(value = "/commodities")
@RestController
public class CommodityController {
    @Autowired
    CommodityService commodityService;

    /**
     * 获取商品列表
     */
    @RequestMapping(method = RequestMethod.GET)
    public PageResponse<CommodityData> findAllCommodities(PageParams pageParams) throws Exception {
        return new PageResponse<>(commodityService.findAllCommodities(pageParams.getPageRequest()));
    }

    /**
     * 获取商品
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public BaseResponse<CommodityData> findCommodity(@PathVariable Long id) throws Exception {
        return new BaseResponse<>(commodityService.findCommodity(id));
    }

    /**
     * 搜索商品
     */
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public PageResponse<CommodityData> searchCommodity(@Valid CommoditySearchParams searchParams) throws Exception {
        return new PageResponse<>(commodityService.searchCommodity(searchParams.getQ(), searchParams.getPageRequest()));
    }

    /**
     * 增加商品
     */
    @RequestMapping(method = RequestMethod.POST)
    public BaseResponse<CommodityData> addCommodity(@RequestBody @Valid BaseRequest<CommodityForm> request) throws Exception {
        return new BaseResponse<>(commodityService.addCommodity(request.getData()));
    }

    /**
     * 修改商品
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public BaseResponse updateCommodity(@RequestBody @Valid BaseRequest<CommodityForm> request, @PathVariable Long id) throws Exception {
        return new BaseResponse<>(commodityService.updateCommodity(request.getData(), id));
    }

    /**
     * 删除商品
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public BaseResponse delete(@PathVariable Long id) throws Exception {
        commodityService.deleteCommodity(id);
        return new BaseResponse();
    }
}
