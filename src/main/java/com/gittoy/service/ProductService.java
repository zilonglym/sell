package com.gittoy.service;


import com.gittoy.dataobject.ProductInfo;
import com.gittoy.dto.CartDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 商品
 *
 * Create By GaoYu 2017/11/13 9:58
 */
public interface ProductService {

    ProductInfo findOne(String productId);

    /**
     * 查询所有在架商品列表
     * @return
     */
    List<ProductInfo> findUpAll();
    
    List<ProductInfo> findUpAllAndNameContaining(String productName);

    Page<ProductInfo> findAll(Pageable pageable);
    
    Page<ProductInfo> findByProductName(Pageable pageable, String productName);

    ProductInfo save(ProductInfo productInfo);

    // 加库存
    void increaseStock(List<CartDTO> cartDTOList);

    // 减库存
    void decreaseStock(List<CartDTO> cartDTOList);

    // 上架
    ProductInfo onSale(String productId);

    // 下架
    ProductInfo offSale(String productId);
    
    // 删除商品
    void delete(String productId);
    
    List<ProductInfo> findByCategoryType(Integer categoryType);
    
    Page<ProductInfo> findByCategoryId(Pageable pageable, Integer categoryType);
    
    Page<ProductInfo> findByCategoryIdAndName(Pageable pageable, Integer categoryType, String productName);
}
