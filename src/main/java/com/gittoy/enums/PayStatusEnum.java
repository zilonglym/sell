package com.gittoy.enums;

import lombok.Getter;

/**
 * PayStatusEnum
 * Create By GaoYu 2017/11/15 8:33
 */
@Getter
public enum PayStatusEnum implements CodeEnum {

    WAIT(0, "等待支付"),
    SUCCESS(1, "支付成功"),
    ;

    private Integer code;

    private String message;

    PayStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
