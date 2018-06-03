package com.gittoy.form;

import java.math.BigDecimal;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;

@Data
public class ClassForm {

	private Integer scoreClass;
	
	/** 区间积分下限 */
	private BigDecimal scoreMin;
	
	/** 区间积分上限 */
	private BigDecimal scoreMax;
	
	/** 分级描述 */
	private String scoreDesc;
}
