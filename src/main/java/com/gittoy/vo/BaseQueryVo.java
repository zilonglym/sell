package com.gittoy.vo;

import lombok.Data;

@Data
public class BaseQueryVo {

	/**
     * 记录条数
     */
    private Integer limit = 50;
    /**
     * 翻阅
     */
    private Integer offset = 0;

    /**
     * 排序方式
     */
    private String order ;

    /**
     * 排序字段
     */
    private String sort;

    public Integer getPageNumber() {
        return  (this.offset + this.limit) / this.limit;
    }
}
