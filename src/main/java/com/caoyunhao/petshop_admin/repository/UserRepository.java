package com.caoyunhao.petshop_admin.repository;

import com.caoyunhao.petshop_admin.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Yunhao.Cao
 * @version 1.0 2018/4/8
 */
@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    User findByUserNameEncryptedAndPassword(String userNameEncrypted, String password);

    int countByUserName(String userName);

    Page<User> findByUserNameLike(String userName,Pageable pageable);
}
