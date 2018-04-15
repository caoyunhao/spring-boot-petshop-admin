package com.caoyunhao.petshop_admin.repository;

import com.caoyunhao.petshop_admin.entity.Category;
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
public interface CategoryRepository extends PagingAndSortingRepository<Category, Long> {
    Category findByCategoryName(String categoryName);

    Page<Category> findByCategoryNameLike(String categoryName, Pageable pageable);

    long countByCategoryName(String cateName);
}
