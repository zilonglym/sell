package com.gittoy.enums;

import lombok.Getter;

/**
 * OrderStatusEnum
 * Create By GaoYu 2017/11/15 8:29
 */
@Getter
public enum OrderStatusEnum implements CodeEnum {
    NEW(0, "新订单"),
    FINISHED(1, "完结"),
    CANCEL(2, "已取消")
    ;

    private Integer code;

    private String message;

    OrderStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
