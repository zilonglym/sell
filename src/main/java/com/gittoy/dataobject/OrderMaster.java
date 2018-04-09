package com.gittoy.dataobject;

import com.gittoy.enums.OrderStatusEnum;
import com.gittoy.enums.PayStatusEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * OrderMaster
 * Create By GaoYu 2017/11/15 8:22
 */
@Entity
@Data
@DynamicUpdate
public class OrderMaster {

    /** 订单id */
    @Id
    private String orderId;

    /** 买家名字 */
    private String buyerName;

    /** 买家电话 */
    private String buyerPhone;

    /** 买家微信地址 */
    private String buyerAddress;
    
    /** 买家留言 */
    private String buyerComment;

    /** 买家微信openid */
    private String buyerOpenid;

    /** 订单总金额 */
    private BigDecimal orderAmount;
    
    /** 配送费用 */
    private BigDecimal orderDeliveryAmount;

    /** 订单状态, 默认0为新下单 */
    private Integer orderStatus = OrderStatusEnum.NEW.getCode();

    /** 支付状态, 默认0为未支付 */
    private Integer payStatus = PayStatusEnum.WAIT.getCode();

    /** 创建时间 */
    private Date createTime;

    /** 修改时间 */
    private Date updateTime;

}

/** ------------------------------------------------------------------

 -- 订单
 create table `order_master` (
 `order_id` varchar(32) not null,
 `buyer_name` varchar(32) not null comment '买家名字',
 `buyer_phone` varchar(32) not null comment '买家电话',
 `buyer_address` varchar(128) not null comment '买家地址',
 `buyer_openid` varchar(64) not null comment '买家微信openid',
 `order_amount` decimal(8,2) not null comment '订单总金额',
 `order_status` tinyint(3) not null default '0' comment '订单状态, 默认0为新下单',
 `pay_status` tinyint(3) not null default '0' comment '支付状态, 默认0为未支付',
 `create_time` timestamp not null default current_timestamp comment '创建时间',
 `update_time` timestamp not null default current_timestamp on update current_timestamp comment '修改时间',
 primary key (`order_id`),
 key `idx_buyer_openid` (`buyer_openid`)
 ) comment '订单表';

 ------------------------------------------------------------------*/