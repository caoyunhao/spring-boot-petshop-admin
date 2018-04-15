package com.caoyunhao.petshop_admin.repository;

import com.caoyunhao.petshop_admin.entity.Custom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * @author Yunhao.Cao
 * @version 1.0 2018/4/8
 */
public interface CustomRepository extends PagingAndSortingRepository<Custom, Long> {

    int countByCustomName(String customName);

    Page<Custom> findAllByCustomNameLike(String userName, Pageable pageable);
}
