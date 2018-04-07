package com.gittoy.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gittoy.dataobject.MembershipClass;

/**
 * MembershipService
 * Create By lzhao 2018/04/07
 */
public interface MembershipClassService {
    List<MembershipClass> findAll(String sellId);
    
    Page<MembershipClass> findList(Pageable pageable);
    
    MembershipClass save(MembershipClass membershipClass);
    
}
