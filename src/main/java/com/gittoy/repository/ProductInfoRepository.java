package com.gittoy.repository;

import com.gittoy.dataobject.ProductInfo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * ProductInfoRepository
 * Create By GaoYu 2017/11/13 9:17
 */
public interface ProductInfoRepository extends JpaRepository<ProductInfo, String> {

    List<ProductInfo> findByProductStatus(Integer productStatus);
    
    List<ProductInfo> findByCategoryType(Integer categoryType);
    
    Page<ProductInfo> findByProductName(Pageable pageable,String productName);
    
    Page<ProductInfo> findByProductNameContaining(Pageable pageable,String productName);
    
    Page<ProductInfo> findByCategoryType(Pageable pageable,Integer categoryType);
    
    Page<ProductInfo> findByCategoryTypeAndProductNameContaining(Pageable pageable,Integer categoryType,String productName);
}
