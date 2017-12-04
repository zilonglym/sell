package com.gittoy.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * HTTP请求返回的最外层对象
 * Create By GaoYu 2017/11/13 14:05
 */
@Data
// @JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultVO<T> implements Serializable {

    private static final long serialVersionUID = 30688373235432543L;

    /** 返回码 */
    private Integer code;

    /** 提示信息 */
    private String msg;

    /** 具体内容 */
    private T data;
}
