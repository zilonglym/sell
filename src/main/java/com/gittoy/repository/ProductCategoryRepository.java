package com.gittoy.repository;

import com.gittoy.dataobject.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * ProductCategoryRepository
 * Create By GaoYu 2017/11/9 16:51
 */
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {

    List<ProductCategory> findByCategoryIdIn(List<Integer> categoryIdList);
}
