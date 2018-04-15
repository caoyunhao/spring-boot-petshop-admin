package com.caoyunhao.petshop_admin.repository;

import com.caoyunhao.petshop_admin.entity.CustomWallet;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author Yunhao.Cao
 * @version 1.0 2018/4/8
 */
public interface CustomWalletRepository extends PagingAndSortingRepository<CustomWallet, Long> {
    void deleteByCustomId(Long id);
}
