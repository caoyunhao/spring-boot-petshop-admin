package com.caoyunhao.petshop_admin.repository;

import com.caoyunhao.petshop_admin.entity.Auth;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Yunhao.Cao
 * @version 1.0 2018/4/8
 */
@Repository
public interface AuthRepository extends PagingAndSortingRepository<Auth, Long> {
    
    Auth findByAuthName(String authName);

    long countByAuthName(String authName);
}
