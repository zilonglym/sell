package com.gittoy.exception;

import com.gittoy.enums.ResultEnum;
import lombok.Getter;

/**
 * SellException
 * Create By GaoYu 2017/11/15 14:06
 */
@Getter
public class SellException extends RuntimeException {

    private Integer code;

    public SellException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public SellException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
