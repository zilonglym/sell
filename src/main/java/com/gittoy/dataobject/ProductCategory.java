package com.gittoy.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import java.io.Serializable;
import java.util.Date;

/**
 * 类目
 * Create By GaoYu 2017/11/9 16:43
 */
@Entity
@DynamicUpdate
@Data
public class ProductCategory implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -370561924920840192L;

	/** 类目id */
    @Id
    @GeneratedValue
    private Integer categoryId;

    /** 类目名字 */
    private String categoryName;

    /** 类目编号 */
    private Integer categoryType;
    
    /** 子类目名字 */
    private String categorySubName;

    /** 子类目编号 */
    private Integer categorySubType;

    /** 创建时间 */
    private Date createTime;

    /** 修改时间 */
    private Date updateTime;

    public ProductCategory() {
    }

    public ProductCategory(String categoryName, Integer categoryType,
    		String categorySubName, Integer categorySubType) {
        this.categoryName = categoryName;
        this.categoryType = categoryType;
        this.categorySubName = categorySubName;
        this.categorySubType = categorySubType;
    }
}

/** ------------------------------------------------------------------

 -- 类目
 create table `product_category` (
 `category_id` int not null auto_increment,
 `category_name` varchar(64) not null comment '类目名字',
 `category_type` int not null comment '类目编号',
 `create_time` timestamp not null default current_timestamp comment '创建时间',
 `update_time` timestamp not null default current_timestamp on update current_timestamp comment '修改时间',
 primary key(`category_id`),
 unique key `uqe_category_type` (`category_type`)
 ) comment '类目表';

 ------------------------------------------------------------------*/
