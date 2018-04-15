package com.caoyunhao.petshop_admin.module.commodity;

import com.caoyunhao.petshop_admin.common.exception.ErrorCode;
import com.caoyunhao.petshop_admin.common.exception.WebBackendException;
import com.caoyunhao.petshop_admin.common.util.ConvertUtil;
import com.caoyunhao.petshop_admin.common.util.DataUtil;
import com.caoyunhao.petshop_admin.common.util.RowConverter;
import com.caoyunhao.petshop_admin.entity.*;
import com.caoyunhao.petshop_admin.module.category.CategoryService;
import com.caoyunhao.petshop_admin.repository.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yunhao.Cao
 * @version 1.0 2018/4/8
 */
@Service
@Transactional
public class CommodityService {
    @Autowired
    CommodityRepository commodityRepository;

    @Autowired
    CommodityStoreRepository commodityStoreRepository;

    @Autowired
    CommodityCategoryRepository commodityCategoryRepository;

    @Autowired
    CommodityImageRepository commodityImageRepository;

    @Autowired
    CategoryService categoryService;

    /**
     * 获取商品列表
     */
    public Page<CommodityData> findAllCommodities(Pageable pageable) throws Exception {
        Page<Commodity> commodityPage = commodityRepository.findAll(pageable);
        return toCommodityDataPage(commodityPage, pageable);
    }

    /**
     * 搜索商品
     */
    public Page<CommodityData> searchCommodity(String commodityName, Pageable pageable) throws Exception {
        Page<Commodity> commodityPage = commodityRepository.findAllByCommodityNameLike(String.format("%%%s%%", commodityName), pageable);
        return toCommodityDataPage(commodityPage, pageable);
    }

    /**
     * 获取商品
     */
    public CommodityData findCommodity(Long commodityId) throws Exception {
        return toCommodityData(getCommodity(commodityId));
    }

    /**
     * 添加商品
     */
    public CommodityData addCommodity(CommodityForm commodityForm) throws Exception {
        Commodity commodity = new Commodity();

        BeanUtils.copyProperties(commodityForm, commodity);

        commodityRepository.save(commodity);
        Long commodityId = commodity.getId();

        CommodityStore commodityStore = new CommodityStore();

        BeanUtils.copyProperties(commodityForm, commodityStore);
        commodityStore.setCommodityId(commodityId);

        commodityStoreRepository.save(commodityStore);
        saveCommodityCategory(generateCommodityCategoryList(commodityForm.getCommodityCategoryIdList(), commodityId));

        return toCommodityData(commodity);
    }

    /**
     * 更新商品
     */
    public CommodityData updateCommodity(CommodityForm commodityForm, Long commodityId) throws Exception {
        // 商品属性相关
        Commodity commodity = new Commodity();
        BeanUtils.copyProperties(commodityForm, commodity);

        commodity.setId(commodityId);
        commodityRepository.save(commodity);

        // 数量相关
        CommodityStore commodityStore = new CommodityStore();
        BeanUtils.copyProperties(commodityForm, commodityStore);

        commodityStore.setCommodityId(commodityId);
        commodityStoreRepository.save(commodityStore);

        // 类目相关
        // 删除已有的“商品类目关系”
        commodityCategoryRepository.deleteByCommodityId(commodityId);
        // 创建新的“商品类目关系”
        saveCommodityCategory(generateCommodityCategoryList(commodityForm.getCommodityCategoryIdList(), commodityId));

        return toCommodityData(commodity);
    }

    /**
     * 删除商品
     */
    public void deleteCommodity(Long commodityId) throws Exception {
        commodityRepository.deleteById(commodityId);
        commodityCategoryRepository.deleteByCommodityId(commodityId);
        commodityStoreRepository.deleteByCommodityId(commodityId);
        commodityImageRepository.deleteByCommodityId(commodityId);
    }

    /**
     * 根据商品和品类列表插入关联
     */
    private List<CommodityCategory> generateCommodityCategoryList(List<Long> categoryIdList, Long commodityId) throws Exception {
        if (categoryIdList == null || categoryIdList.size() == 0) {
            return null;
        }
        List<CommodityCategory> commodityCategoryList = new ArrayList<>();
        for (Long categoryId : categoryIdList) {
            CommodityCategory commodityCategory = new CommodityCategory();
            commodityCategory.setCommodityId(commodityId);
            commodityCategory.setCategoryId(categoryId);
            commodityCategoryList.add(commodityCategory);
        }
        return commodityCategoryList;
    }

    /**
     * 根据商品Id获取商品的Category列表
     */
    private List<Category> getCommodityCategoryList(Long commodityId) throws Exception {
        return categoryService.findAllCategoriesByCommodityId(commodityId);
    }

    /**
     * 从Repository取商品
     */
    public Commodity getCommodity(Long commodityId)
            throws WebBackendException {
        return getCommodity(commodityId, true, null);
    }

    public Commodity getCommodity(Long commodityId, boolean throw_, Commodity orElse)
            throws WebBackendException {
        return DataUtil.getOrElse(id_ -> commodityRepository.findById(id_), commodityId, throw_);
    }

    public CommodityStore getCommodityStore(Long commodityId)
            throws WebBackendException {
        return DataUtil.getOrElse(id -> commodityStoreRepository.findByCommodityId(id), commodityId, false);
    }

    public void saveCommodityCategory(List<CommodityCategory> commodityCategoryList) {
        if (commodityCategoryList == null || commodityCategoryList.isEmpty()) return;
        commodityCategoryRepository.saveAll(commodityCategoryList);
    }

    /**
     * 将商品Commodity商品转变为CommodityData
     */
    private CommodityData toCommodityData(Commodity commodity) throws Exception {
        CommodityData commodityData = new CommodityData();
        BeanUtils.copyProperties(commodity, commodityData);

        Long commodityId = commodity.getId();

        commodityData.setCommodityCategoryList(getCommodityCategoryList(commodityId));

        CommodityStore commodityStore = getCommodityStore(commodityId);
        if (null != commodityStore) {
            BeanUtils.copyProperties(commodityStore, commodityData, "id");
        }

        return commodityData;
    }

    /**
     * 将Page<Commodity>转变为Page<CommodityData>
     */
    private Page<CommodityData> toCommodityDataPage(Page<Commodity> commodityPage, Pageable pageable) throws Exception {
        return ConvertUtil.convertPage(commodityPage, pageable, commodityPage.getTotalElements(), new RowConverter<Commodity, CommodityData>() {
            @Override
            public CommodityData convertRow(Commodity commodity) throws Exception {
                return toCommodityData(commodity);
            }
        });
    }
}
