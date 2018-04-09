package com.gittoy.enums;

import lombok.Getter;

@Getter
public enum ScoreStatusEnum implements CodeEnum {
    CLOSE(0, "已关闭"),
    OPEN(1, "已打开")
    ;

    private Integer code;

    private String message;

    ScoreStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
