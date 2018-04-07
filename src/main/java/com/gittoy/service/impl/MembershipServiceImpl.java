package com.gittoy.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gittoy.dataobject.MembershipInfo;
import com.gittoy.repository.MembershipRepository;
import com.gittoy.service.MembershipService;

import lombok.extern.slf4j.Slf4j;

/**
 * MembershipServiceImpl
 * Create By lzhao 2018/04/07
 */
@Service
@Slf4j
public class MembershipServiceImpl implements MembershipService {

	@Autowired
    private MembershipRepository membershipRepository;
	
	@Override
	public MembershipInfo findOne(String openid) {
		return membershipRepository.findOne(openid);
	}

	@Override
	public Page<MembershipInfo> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MembershipInfo save(MembershipInfo membershipInfo) {
		return membershipRepository.save(membershipInfo);
	}

	@Override
	public void update(MembershipInfo membershipinfo) {
		// TODO Auto-generated method stub

	}

	/**
     * 查询会员列表：所有
     *
     * @param pageable
     */
	@Override
	public Page<MembershipInfo> findList(Pageable pageable) {
		Page<MembershipInfo> membershipInfoPage = membershipRepository.findAll(pageable);
		return new PageImpl<>(membershipInfoPage.getContent(), pageable, membershipInfoPage.getTotalElements());
	}
}
