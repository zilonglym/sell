package com.gittoy.service.impl;

import com.gittoy.dataobject.ProductCategory;
import com.gittoy.repository.ProductCategoryRepository;
import com.gittoy.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 类目
 * Create By GaoYu 2017/11/10 10:21
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private ProductCategoryRepository repository;

    @Override
    public ProductCategory findOne(Integer categoryId) {
        return repository.findOne(categoryId);
    }

    @Override
    public List<ProductCategory> findAll() {
        return repository.findAll();
    }

    @Override
    public List<ProductCategory> findByCategoryIdIn(List<Integer> categoryIdList) {
        return repository.findByCategoryIdIn(categoryIdList);
    }

    @Override
    public ProductCategory save(ProductCategory productCategory) {
        return repository.save(productCategory);
    }

	@Override
	public void delete(Integer categoryId) {
		repository.delete(categoryId);
	}
}
