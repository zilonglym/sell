package com.gittoy.vo;

import java.util.Date;

import lombok.Data;

@Data
public class SalesQueryVo extends BaseQueryVo {

	/**
	 * 产品大类
	 */
	private String categoryName;
	
	/**
	 * 订单号
	 */
	private String orderId;
	/**
	 * 查询起始日期
	 */
	private Date startDate;
	
	/**
	 * 查询结束日期
	 */
	private Date endDate;
}
