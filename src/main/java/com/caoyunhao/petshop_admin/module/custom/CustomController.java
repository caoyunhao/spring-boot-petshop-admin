package com.caoyunhao.petshop_admin.module.custom;

import com.caoyunhao.petshop_admin.common.BaseRequest;
import com.caoyunhao.petshop_admin.common.page.PageParams;
import com.caoyunhao.petshop_admin.common.page.SearchPageParams;
import com.caoyunhao.petshop_admin.common.response.BaseResponse;
import com.caoyunhao.petshop_admin.common.response.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Yunhao.Cao
 * @version 1.0 2018/4/8
 */
@RestController
@RequestMapping("/customs")
public class CustomController {

    @Autowired
    CustomService customService;

    /**
     * 获取顾客列表
     */
    @RequestMapping(method = RequestMethod.GET)
    public PageResponse<CustomData> findCustoms(PageParams pageParams) throws Exception {
        return new PageResponse<>(customService.findCustoms(pageParams.getPageRequest()));
    }

    /**
     * 搜索顾客，返回列表
     */
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public PageResponse<CustomData> searchCustoms(SearchPageParams searchPageParams) throws Exception {
        return new PageResponse<>(customService.searchByCustomName(searchPageParams.getQ(), searchPageParams.getPageRequest()));
    }

    /**
     * 获取顾客
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public BaseResponse<CustomData> findCustom(@PathVariable Long id) throws Exception {
        return new BaseResponse<>(customService.findCustomById(id));
    }

    /**
     * 增加顾客
     */
    @RequestMapping(method = RequestMethod.POST)
    public BaseResponse<CustomData> addCustom(@RequestBody @Valid BaseRequest<CustomFormWithPassword> request) throws Exception {
        return new BaseResponse<>(customService.addCustom(request.getData()));
    }

    /**
     * 修改顾客, 不包括密码
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public BaseResponse<CustomData> updateCustom(@RequestBody @Valid BaseRequest<CustomForm> request, @PathVariable Long id) throws Exception {
        return new BaseResponse<>(customService.updateCustom(request.getData(), id));
    }

    /**
     * 删除顾客
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public BaseResponse deleteCustom(@PathVariable Long id) throws Exception {
        customService.deleteCustom(id);
        return new BaseResponse();
    }
}
