package com.caoyunhao.petshop_admin.module.category;

import com.caoyunhao.petshop_admin.common.BaseRequest;
import com.caoyunhao.petshop_admin.common.page.PageParams;
import com.caoyunhao.petshop_admin.common.response.BaseResponse;
import com.caoyunhao.petshop_admin.common.response.PageResponse;
import com.caoyunhao.petshop_admin.entity.Category;
import com.caoyunhao.petshop_admin.entity.Commodity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author Yunhao.Cao
 * @version 1.0 2018/4/8
 */
@RestController
@RequestMapping(value = "/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 添加品类
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.POST)
    public BaseResponse addCategory(@RequestBody BaseRequest<Category> request) throws Exception {
        categoryService.addCategory(request.getData());
        return new BaseResponse();
    }

    /**
     * 分页显示品类列表
     * @param pageParams
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.GET)
    public PageResponse<Category>  findAllCategories(PageParams pageParams) throws Exception {
        return new PageResponse<>(categoryService.findAllCategories(pageParams.getPageRequest()));
    }

    /**
     * 删除品类
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public BaseResponse deleteCategoryById(@PathVariable Long id) throws Exception {
        categoryService.deleteCategory(id);
        return new BaseResponse();
    }

    /**
     * 更新品类
     * @param request
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public BaseResponse updateCategory(@RequestBody  BaseRequest<Category> request,@PathVariable Long id) throws Exception {
        categoryService.updateCategory(request.getData(),id);
        return new BaseResponse();
    }

    /**
     * 根据id查找品类信息
     * @param id
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public BaseResponse findCategoryById(@PathVariable Long id) throws Exception{
        return new BaseResponse<>(categoryService.findByCategoryId(id));
    }

    /**
     * 模糊查询品类列表
     * @param cateName
     * @param pageParams
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/search/{cateName}",method = RequestMethod.GET)
    public PageResponse<Category> searchAllByCateName(@PathVariable(name = "cateName") String cateName,PageParams pageParams) throws Exception {
        return new PageResponse<>(categoryService.findAllByCategorySearchName("%"+cateName+"%",pageParams.getPageRequest()));
    }

    /**
     * 根据商品所属的所有品类
     * @param categoryId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/showAllCommodities/{categoryId}",method = RequestMethod.GET)
    public PageResponse<Commodity> findCommoditiesByCategoryId(@PathVariable(name = "categoryId") long categoryId,PageParams pageParams) throws Exception {
        return new PageResponse<>(categoryService.findAllCommoditiesByCategoryId(categoryId,pageParams.getPageRequest()));
    }
}
