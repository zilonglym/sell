package com.gittoy.repository;

import com.gittoy.dataobject.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * OrderDetailRepository
 * Create By GaoYu 2017/11/15 8:53
 */
public interface OrderDetailRepository extends JpaRepository<OrderDetail, String> {

    List<OrderDetail> findByOrderId(String orderId);
}
