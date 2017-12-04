package com.gittoy.service;

import com.gittoy.dataobject.ProductCategory;

import java.util.List;

/**
 * 类目
 * Create By GaoYu 2017/11/10 10:04
 */
public interface CategoryService {

    ProductCategory findOne(Integer categoryId);

    List<ProductCategory> findAll();

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

    ProductCategory save(ProductCategory productCategory);
}
