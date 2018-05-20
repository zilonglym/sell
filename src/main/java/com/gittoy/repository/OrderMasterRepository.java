package com.gittoy.repository;

import com.gittoy.dataobject.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * OrderMasterRepository
 * Create By GaoYu 2017/11/15 8:48
 */
public interface OrderMasterRepository extends JpaRepository<OrderMaster, String> {

    Page<OrderMaster> findByBuyerOpenid(String buyerOpenid, Pageable pageable);
    
    Page<OrderMaster> findByBuyerNameContaining(String buyerName, Pageable pageable);
    
    Page<OrderMaster> findByBuyerPhoneContaining(String buyerPhone, Pageable pageable);
    
    Page<OrderMaster> findByBuyerNameContainingAndBuyerPhoneContaining(String buyerName, String buyerPhone, Pageable pageable);
}
