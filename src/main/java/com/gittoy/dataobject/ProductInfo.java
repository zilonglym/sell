package com.gittoy.dataobject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gittoy.enums.ProductStatusEnum;
import com.gittoy.utils.EnumUtil;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品
 *
 * Create By GaoYu 2017/11/13 9:09
 */
@Entity
@Data
@DynamicUpdate
public class ProductInfo {

    @Id
    private String productId;

    /** 名字 */
    private String productName;

    /** 单价 */
    private BigDecimal productPrice;

    /** 库存 */
    private Integer productStock;

    /** 描述 */
    private String productDescription;

    /** 小图 */
    private String productIcon;

    /** 状态，0正常1下架 */
    private Integer productStatus = ProductStatusEnum.UP.getCode();

    /** 类目编号 */
    private Integer categoryType;

    /** 创建时间 */
    private Date createTime;

    /** 修改时间 */
    private Date updateTime;

    /** 商品在架状态 */
    @JsonIgnore
    public ProductStatusEnum getProductStatusEnum() {
        return EnumUtil.getByCode(productStatus, ProductStatusEnum.class);
    }
}

/** ------------------------------------------------------------------

 -- 商品
 create table `product_info` (
 `product_id` varchar(32) not null,
 `product_name` varchar(64) not null comment '商品名称',
 `product_price` decimal(8,2) not null comment '单价',
 `product_stock` int not null comment '库存',
 `product_description` varchar(64) comment '描述',
 `product_icon` varchar(512) comment '小图',
 `product_status` tinyint(3) DEFAULT '0' COMMENT '商品状态,0正常1下架',
 `category_type` int not null comment '类目编号',
 `create_time` timestamp not null default current_timestamp comment '创建时间',
 `update_time` timestamp not null default current_timestamp on update current_timestamp comment '修改时间',
 primary key (`product_id`)
 ) comment '商品表';

 ------------------------------------------------------------------*/