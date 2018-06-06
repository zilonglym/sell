package com.gittoy.service.impl;

import com.gittoy.dataobject.SellerInfo;
import com.gittoy.repository.SellerInfoRepository;
import com.gittoy.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * SellerServiceImpl
 * Create By GaoYu 2017/11/24 13:12
 */
@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    private SellerInfoRepository repository;

    /**
     * 通过openid查询卖家端信息
     *
     * @param openid
     * @return
     */
    @Override
    public SellerInfo findSellerInfoByOpenid(String openid) {
        return repository.findByOpenid(openid);
    }

	@Override
	public SellerInfo findSellerInfoBySellerId(String sellerId) {
		return repository.findBySellerId(sellerId);
	}
	
	@Override
	public SellerInfo findSellerInfoByUsername(String username) {
		return repository.findByUsername(username);
	}

	@Override
	public void save(SellerInfo sellerInfo) {
		repository.save(sellerInfo);
	}
}
