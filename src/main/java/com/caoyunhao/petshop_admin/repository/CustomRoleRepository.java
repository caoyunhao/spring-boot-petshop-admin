package com.caoyunhao.petshop_admin.repository;

import com.caoyunhao.petshop_admin.entity.CustomRole;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author jie.yao
 * @date 2017/9/26
 */
@Repository
public interface CustomRoleRepository extends PagingAndSortingRepository<CustomRole, Long> {

    void deleteByCustomId(Long userId);

    List<CustomRole> findByCustomId(Long userId);

    void deleteByRoleId(Long roleId);
}
