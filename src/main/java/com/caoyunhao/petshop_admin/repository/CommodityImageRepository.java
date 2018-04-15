package com.caoyunhao.petshop_admin.repository;

import com.caoyunhao.petshop_admin.entity.CommodityImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * @author Yunhao.Cao
 * @version 1.0 2018/4/8
 */
public interface CommodityImageRepository extends PagingAndSortingRepository<CommodityImage, Long> {

    CommodityImage findByCommodityId(Long commodityId);

    void deleteByCommodityId(Long commodityId);

    void deleteAllByCommodityId(Long commodityId);
}
