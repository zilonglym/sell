package com.gittoy.dataobject;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.gittoy.vo.BaseQueryVo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * OrderDetail
 * Create By GaoYu 2017/11/15 8:37
 */
@Entity
@Data
public class OrderDetail extends BaseQueryVo{

    /** 详情id */
    @Id
    private String detailId;

    /** 订单id */
    private String orderId;

    /** 商品id */
    private String productId;

    /** 商品名称 */
    private String productName;

    /** 商品价格，单位分 */
    private BigDecimal productPrice;

    /** 数量 */
    private Integer productQuantity;

    /** 小图 */
    private String productIcon;

    /** 创建时间 */
    private Date createTime;
    
    public OrderDetail(){}
    
    public OrderDetail(String detailId, String orderId, String productId, String productName, BigDecimal productPrice, Integer productQuantity, String productIcon, Date createTime){
    	this.detailId = detailId;
    	this.orderId = orderId;
    	this.productId = productId;
    	this.productName = productName;
    	this.productPrice = productPrice;
    	this.productQuantity = productQuantity;
    	this.productIcon = productIcon;
    	this.createTime = createTime;
    }
}

/** ------------------------------------------------------------------

 -- 订单详情
 create table `order_detail` (
 `detail_id` varchar(32) not null,
 `order_id` varchar(32) not null,
 `product_id` varchar(32) not null,
 `product_name` varchar(64) not null comment '商品名称',
 `product_price` decimal(8,2) not null comment '当前价格，单位分',
 `product_quantity` int not null comment '数量',
 `product_icon` varchar(512) comment '小图',
 `create_time` timestamp not null default current_timestamp comment '创建时间',
 `update_time` timestamp not null default current_timestamp on update current_timestamp comment '修改时间',
 primary key (`detail_id`),
 key `idx_order_id` (`order_id`)
 ) comment '订单详情表';

 ------------------------------------------------------------------*/