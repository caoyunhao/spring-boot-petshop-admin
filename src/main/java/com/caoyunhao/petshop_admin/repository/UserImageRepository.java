package com.caoyunhao.petshop_admin.repository;

import com.caoyunhao.petshop_admin.entity.UserImage;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Yunhao.Cao
 * @version 1.0 2018/4/8
 */
@Repository
public interface UserImageRepository extends PagingAndSortingRepository<UserImage,Long> {
    void deleteAllByUserId(Long userId);

    UserImage findByUserId(Long userId);
}
