package com.gittoy.service;


import com.gittoy.dto.OrderDTO;

/**
 * 推送消息
 * Create By GaoYu 2017/11/27 14:11
 */
public interface PushMessageService {

    /**
     * 订单状态变更消息
     * @param orderDTO
     */
    void orderStatus(OrderDTO orderDTO);
}
