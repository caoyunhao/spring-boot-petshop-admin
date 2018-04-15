package com.caoyunhao.petshop_admin.repository;

import com.caoyunhao.petshop_admin.entity.UserRole;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author jie.yao
 * @date 2017/9/26
 */
@Repository
public interface UserRoleRepository extends PagingAndSortingRepository<UserRole, Long> {

    void deleteByUserId(Long userId);

    List<UserRole> findByUserId(Long userId);

    void deleteByRoleId(Long roleId);
}
