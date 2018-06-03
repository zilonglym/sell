package com.gittoy.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gittoy.dataobject.MembershipClass;

/**
 * MembershipService
 * Create By lzhao 2018/04/07
 */
public interface MembershipClassService {
	
	MembershipClass findByScoreClass(Integer scoreClass);
    
    Page<MembershipClass> findAll(Pageable pageable);
    
    MembershipClass save(MembershipClass membershipClass);

	void delete(Integer scoreClass);
	
}
