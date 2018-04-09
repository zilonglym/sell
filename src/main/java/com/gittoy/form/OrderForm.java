package com.gittoy.form;

import java.math.BigDecimal;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;

/**
 * OrderForm
 * Create By GaoYu 2017/11/17 8:28
 */
@Data
public class OrderForm {

    /**
     * 买家姓名
     */
    @NotEmpty(message = "姓名必填")
    private String name;

    /**
     * 买家手机号
     */
    @NotEmpty(message = "手机号必填")
    private String phone;

    /**
     * 买家地址
     */
    @NotEmpty(message = "地址必填")
    private String address;

    /**
     * 买家微信openid
     */
    @NotEmpty(message = "openid不能为空")
    private String openid;

    /**
     * 购物车
     */
    @NotEmpty(message = "购物车不能为空")
    private String items;
    
    /** 备注 */
    private String comment;
    
    /** 配送费用 */
    private BigDecimal delivery;
}
