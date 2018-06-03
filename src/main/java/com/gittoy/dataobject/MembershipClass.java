package com.gittoy.dataobject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.IdClass;

import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;

/**
 * MembershipClass
 * Create By lzhao 2018/04/07
 */
@Entity
@Data
@DynamicUpdate
public class MembershipClass implements Serializable {
	
	private static final long serialVersionUID = 1L;
	/** 积分区间*/
	@Id
	@GeneratedValue
	private Integer scoreClass;
	
	/** 区间积分下限 */
	private BigDecimal scoreMin;
	
	/** 区间积分上限 */
	private BigDecimal scoreMax;
	
	/** 分级描述 */
	private String scoreDesc;
	
    /** 创建时间 */
    private Date createTime;
    
    /** 修改时间 */
    private Date updateTime;
	
}
