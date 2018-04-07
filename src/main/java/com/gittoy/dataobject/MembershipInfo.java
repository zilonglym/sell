package com.gittoy.dataobject;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gittoy.enums.MembershipClassEnum;
import com.gittoy.enums.OrderStatusEnum;
import com.gittoy.utils.EnumUtil;

import lombok.Data;

/**
 * MembershipRepositoryTest
 * Create By lzhao 2018/04/07
 */
@Entity
@Data
@DynamicUpdate
public class MembershipInfo {
	
	/** 序列号 */
	private String id;
	
	/** 昵称 */
	private String username;
	
	/** 微信openid */
	@Id
	private String openid;
	
	/** 总购买金额 */
	private BigDecimal purchaseAmount;
	
	/** 当前积分 */
	private BigDecimal score;
	
	/** 会员等级  默认是青铜等级*/
	private Integer scoreGrade = MembershipClassEnum.Bronze.getCode();
	
    /** 创建时间 */
    private Date createTime;
    
    /** 修改时间 */
    private Date updateTime;
    
    @JsonIgnore
    public MembershipClassEnum getMembershipClassEnum() {
        return EnumUtil.getByCode(scoreGrade, MembershipClassEnum.class);
    }
}
