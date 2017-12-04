package com.gittoy.enums;

import lombok.Getter;

/**
 * 商品状态
 * Create By GaoYu 2017/11/13 10:07
 */
@Getter
public enum ProductStatusEnum implements CodeEnum {

    UP(0, "上架"),
    DOWN(1, "下架");

    private Integer code;

    private String message;

    ProductStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
