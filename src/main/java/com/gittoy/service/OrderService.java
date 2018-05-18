package com.gittoy.service;

import com.github.pagehelper.PageInfo;
import com.gittoy.dataobject.OrderDetail;
import com.gittoy.dataobject.OrderMaster;
import com.gittoy.dto.OrderDTO;
import com.gittoy.vo.SalesQueryVo;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * OrderService
 * Create By GaoYu 2017/11/15 10:26
 */
public interface OrderService {

    /** 创建订单 */
    OrderDTO create(OrderDTO orderDTO);

    /** 查询单个订单 */
    OrderDTO findOne(String orderId);

    /** 查询订单列表：根据客户 */
    Page<OrderDTO> findList(String buyerOpenid, Pageable pageable);

    /** 取消订单 */
    OrderDTO cancel(OrderDTO orderDTO);

    /** 完结订单 */
    OrderDTO finish(OrderDTO orderDTO);

    /** 支付订单 */
    OrderDTO paid(OrderDTO orderDTO);

    /** 查询订单列表：所有 */
    Page<OrderDTO> findList(Pageable pageable);
    
    /** 查询销售流水     */
    PageInfo<OrderDetail> findSalesList(SalesQueryVo queryVo);
    
    List<OrderDetail> findSalesListByOrderId(Pageable pageable, SalesQueryVo queryVo);
    /** 默认查询所有销售流水 */
    List<OrderDetail> findAllSalesList(Pageable pageable, SalesQueryVo queryVo);
    /** 按产品大类分 */
    List<OrderDetail> findAllSalesListByCategory(Pageable pageable, SalesQueryVo queryVo);
    
    List<OrderDetail> findDetailListByPayStatus(Pageable pageable, SalesQueryVo queryVo);
    
    /** 按时间查询 */
    List<OrderDetail> findByCreateTimeBetween(Pageable pageable, SalesQueryVo queryVo);
    /** 按产品大类 + 时间分 */
    List<OrderDetail> findPaidOrderDetailByCategoryAndCreateTime(Pageable pageable, SalesQueryVo queryVo);
    
    Long countOrderDetailByOrderId(String orderId);
    
    Long countOrderDetail();
    
    Long countOrderDetailByPayStatus();
    
    Long countOrderDetailByCreateTimeBetween(Date startDate,Date endDate);
    
    Long countOrderDetailByCategoryName(String categoryName);

	Long countOrderDetailByCategoryNameAndCreateTime(SalesQueryVo queryVo);
	
	/** 姓名模糊查询 */
	Page<OrderMaster> findByBuyerName(String buyerName, Pageable pageable);
	/** 手机号模糊查询 */
	Page<OrderMaster> findByBuyerPhone(String buyerPhone, Pageable pageable);
	/** 姓名+手机号模糊查询 */
	Page<OrderMaster> findByBuyerNameAndPhone(String buyerName, String buyerPhone, Pageable pageable);
}
