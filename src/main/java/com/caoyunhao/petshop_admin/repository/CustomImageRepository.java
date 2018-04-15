package com.caoyunhao.petshop_admin.repository;

import com.caoyunhao.petshop_admin.entity.CustomImage;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Yunhao.Cao
 * @version 1.0 2018/4/8
 */
@Repository
public interface CustomImageRepository extends PagingAndSortingRepository<CustomImage,Long> {
    CustomImage findByCustomId(Long CustomId);

    void deleteAllByCustomId(Long userId);
}
