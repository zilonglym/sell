package com.gittoy.form;

import lombok.Data;

/**
 * CategoryForm
 * Create By GaoYu 2017/11/24 8:47
 */
@Data
public class CategoryForm {

    /** 类目id */
    private Integer categoryId;

    /** 类目名字 */
    private String categoryName;

    /** 类目编号 */
    private Integer categoryType;
}
