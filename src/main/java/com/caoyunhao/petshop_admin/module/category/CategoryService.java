package com.caoyunhao.petshop_admin.module.category;

import com.caoyunhao.petshop_admin.common.exception.ErrorCode;
import com.caoyunhao.petshop_admin.common.exception.WebBackendException;
import com.caoyunhao.petshop_admin.common.util.DataUtil;
import com.caoyunhao.petshop_admin.entity.Category;
import com.caoyunhao.petshop_admin.entity.Commodity;
import com.caoyunhao.petshop_admin.entity.CommodityCategory;
import com.caoyunhao.petshop_admin.module.commodity.CommodityService;
import com.caoyunhao.petshop_admin.repository.CategoryRepository;
import com.caoyunhao.petshop_admin.repository.CommodityCategoryRepository;
import com.caoyunhao.petshop_admin.repository.CommodityRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Yunhao.Cao
 * @version 1.0 2018/4/8
 */
@Service
@Transactional
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CommodityCategoryRepository commodityCategoryRepository;
    @Autowired
    private CommodityService commodityService;

    /**
     * 添加品类
     *
     * @param category
     * @throws Exception
     */
    public void addCategory(Category category) throws Exception {
        if (category == null || StringUtils.isEmpty(category.getCategoryName())) {
            throw new WebBackendException(ErrorCode.OTHER);
        }
        if (categoryRepository.findByCategoryName(category.getCategoryName()) != null) {
            throw new WebBackendException(ErrorCode.CATEGORY_EXISTS);
        }
        Category targetCategory = new Category();
        BeanUtils.copyProperties(category, targetCategory, "id");
        categoryRepository.save(targetCategory);
    }

    /**
     * 删除品类
     *
     * @param categoryId
     * @throws Exception
     */
    public void deleteCategory(Long categoryId) throws Exception {
        categoryRepository.deleteById(categoryId);
        commodityCategoryRepository.deleteByCategoryId(categoryId);
    }

    /**
     * 更新品类
     *
     * @param category
     * @throws Exception
     */
    public void updateCategory(Category category, Long id) throws Exception {
        if (null == category || StringUtils.isEmpty(category.getCategoryName())) {
            throw new WebBackendException(ErrorCode.OTHER);
        }
        if ((!category.getCategoryName().equals(getCategory(id).getCategoryName())) && categoryRepository.countByCategoryName(category.getCategoryName()) > 0) {
            throw new WebBackendException(ErrorCode.CATEGORY_EXISTS);
        }
        category.setId(id);
        categoryRepository.save(category);
    }

    /**
     * 根据id查找品类
     *
     * @param categoryId
     * @return
     * @throws Exception
     */
    public Category findByCategoryId(Long categoryId) throws Exception {
        return getCategory(categoryId);
    }

    /**
     * 展示商品分页
     *
     * @param pageable
     * @return
     * @throws Exception
     */

    public Page<Category> findAllCategories(Pageable pageable) throws Exception {
        return categoryRepository.findAll(pageable);
    }

    /**
     * 根据品类名称模糊搜索
     *
     * @param categoryName
     * @param pageable
     * @return
     * @throws Exception
     */
    public Page<Category> findAllByCategorySearchName(String categoryName, Pageable pageable) throws Exception {
        return categoryRepository.findByCategoryNameLike(categoryName, pageable);
    }

    /**
     * 展示品类的所有商品
     *
     * @param categoryId
     * @return
     * @throws Exception
     */
    public Page<Commodity> findAllCommoditiesByCategoryId(Long categoryId, Pageable pageable) throws Exception {
        getCategory(categoryId);

        if (commodityCategoryRepository.findAllByCategoryId(categoryId) == null) {
            throw new WebBackendException(ErrorCode.CATEGORY_HAVE_NO_COMMODITY);
        }
        List<Commodity> commodityList = new ArrayList<>();
        for (CommodityCategory commodityCategory :
                commodityCategoryRepository.findAllByCategoryId(categoryId)) {
            commodityList.add(commodityService.getCommodity(commodityCategory.getCommodityId()));
        }
        return new PageImpl<>(commodityList, pageable, commodityList.size());
    }

    /**
     * 根据商品id查询品类列表
     *
     * @param commodityId
     * @return
     * @throws Exception
     */
    public List<Category> findAllCategoriesByCommodityId(Long commodityId) throws Exception {
        List<CommodityCategory> commodityCategoryList;
        if ((commodityCategoryList = commodityCategoryRepository.findAllByCommodityId(commodityId)).size() == 0) {
            return null;
        }
        List<Category> categoryList = new ArrayList<>();
        for (CommodityCategory commodityCategory : commodityCategoryList) {
            categoryList.add(getCategory(commodityCategory.getCategoryId()));
        }
        return categoryList;
    }

    public Category getCategory(Long id)
            throws WebBackendException {
        return getCategory(id, false);
    }

    public Category getCategory(Long id, boolean throw_)
            throws WebBackendException {
        return DataUtil.getOrElse(id_ -> categoryRepository.findById(id_), id, throw_);
    }
}
