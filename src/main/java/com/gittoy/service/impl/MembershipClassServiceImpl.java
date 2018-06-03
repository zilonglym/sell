package com.gittoy.service.impl;

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
	public Page<MembershipClass> findAll(Pageable pageable) {
		return membershipClassRepository.findAll(pageable);
	}

	@Override
	public MembershipClass save(MembershipClass membershipClass) {
		return membershipClassRepository.save(membershipClass);
	}
	
	@Override
	public void delete(Integer scoreClass) {
		membershipClassRepository.delete(scoreClass);
	}

	@Override
	public MembershipClass findByScoreClass(Integer scoreClass) {
		return membershipClassRepository.findByScoreClass(scoreClass);
	}

}
