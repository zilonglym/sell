package com.gittoy.repository;

import com.gittoy.dataobject.SellerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * SellerInfoRepository
 * Create By GaoYu 2017/11/24 12:48
 */
public interface SellerInfoRepository extends JpaRepository<SellerInfo, String> {
    SellerInfo findByOpenid(String openid);
}
