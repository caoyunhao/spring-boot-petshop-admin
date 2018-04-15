package com.caoyunhao.petshop_admin.repository;

import com.caoyunhao.petshop_admin.entity.CommodityStore;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Yunhao.Cao
 * @version 1.0 2018/4/8
 */
@Repository
public interface CommodityStoreRepository extends PagingAndSortingRepository<CommodityStore, Long> {

    Optional<CommodityStore> findByCommodityId(Long commodityId);

    void deleteByCommodityId(Long commodityId);
}
