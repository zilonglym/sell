package com.gittoy.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gittoy.dataobject.MembershipInfo;
/**
 * MembershipService
 * Create By lzhao 2018/04/07
 */
public interface MembershipService {
	
	MembershipInfo findOne(String openid);
	
    Page<MembershipInfo> findAll(Pageable pageable);
    
    Page<MembershipInfo> findList(Pageable pageable);
    
    MembershipInfo save(MembershipInfo productInfo);
    
    // 更新金额和积分
    void update(MembershipInfo membershipinfo);

}
