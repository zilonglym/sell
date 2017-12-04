package com.gittoy.dto;

import lombok.Data;

/**
 * CartDTO
 * Create By GaoYu 2017/11/15 15:12
 */
@Data
public class CartDTO {

    /** 商品id */
    private String productId;

    /** 数量 */
    private Integer productQuantity;

    public CartDTO(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
