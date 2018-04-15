package com.caoyunhao.petshop_admin.repository;

import com.caoyunhao.petshop_admin.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
/**
 * @author Yunhao.Cao
 * @version 1.0 2018/4/8
 */
@Repository
public interface RoleRepository extends PagingAndSortingRepository<Role,Long> {

    Page<Role> findByRoleNameLike(String roleName,Pageable pageable);

    int countByRoleName(String roleName);
}
