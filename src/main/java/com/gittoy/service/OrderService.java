package com.gittoy.service;

import com.github.pagehelper.PageInfo;
import com.gittoy.dataobject.OrderDetail;
import com.gittoy.dto.OrderDTO;
import com.gittoy.vo.SalesQueryVo;

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
    
    PageInfo<OrderDetail> findSalesListByOpenId(SalesQueryVo queryVo);

}
