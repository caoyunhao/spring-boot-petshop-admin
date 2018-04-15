package com.caoyunhao.petshop_admin.repository;

import com.caoyunhao.petshop_admin.entity.RoleAuth;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


//Created by Li.Hou on 2017/10/11.

@Repository
public interface RoleAuthRepository extends PagingAndSortingRepository<RoleAuth, Long> {

    List<RoleAuth> findByRoleId(Long roleId);

    void deleteAllByRoleId(Long roleId);

    void deleteByAuthId(long authId);

}
