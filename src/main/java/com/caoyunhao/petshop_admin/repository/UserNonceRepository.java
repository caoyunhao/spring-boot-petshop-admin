package com.caoyunhao.petshop_admin.repository;

import com.caoyunhao.petshop_admin.entity.UserNonce;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author Yunhao.Cao
 * @version 1.0 2018/4/8
 */
public interface UserNonceRepository extends PagingAndSortingRepository<UserNonce, Long> {
    Optional<UserNonce> findByUserId(Long id);

    void deleteByUserId(Long id);
}
