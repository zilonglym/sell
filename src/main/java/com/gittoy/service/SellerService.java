package com.gittoy.service;

import com.gittoy.dataobject.SellerInfo;

/**
 * SellerService
 * Create By GaoYu 2017/11/24 13:10
 */
public interface SellerService {

    /**
     * 通过openid查询卖家端信息
     *
     * @param openid
     * @return
     */
    SellerInfo findSellerInfoByOpenid(String openid);
    
    SellerInfo findSellerInfoBySellerId(String sellerId);
    
    void save(SellerInfo sellerInfo);

	SellerInfo findSellerInfoByUsername(String username);
}
