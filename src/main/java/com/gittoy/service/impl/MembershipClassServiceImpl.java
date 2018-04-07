package com.gittoy.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gittoy.dataobject.MembershipClass;
import com.gittoy.repository.MembershipClassRepository;
import com.gittoy.service.MembershipClassService;


/**
 * MembershipClassServiceImpl
 * Create By lzhao 2018/04/07
 */
@Service
public class MembershipClassServiceImpl implements MembershipClassService {

	@Autowired
	private MembershipClassRepository membershipClassRepository;
	
	@Override
	public List<MembershipClass> findAll(String sellId) {
		return membershipClassRepository.findBySellIdIn(sellId);
	}

	@Override
	public Page<MembershipClass> findList(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MembershipClass save(MembershipClass membershipClass) {
		return membershipClassRepository.save(membershipClass);
	}

}
