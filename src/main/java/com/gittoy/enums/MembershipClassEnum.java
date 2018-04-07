package com.gittoy.enums;

import lombok.Getter;

/**
 * 会员等级
 * Create By lzhao 2017/11/13 10:07
 */
@Getter
public enum MembershipClassEnum implements CodeEnum {

	Bronze(1, "青铜"),
	Silver (2, "白银"),
	Gold (3, "黄金");

    private Integer code;

    private String message;

    MembershipClassEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}