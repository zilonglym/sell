package com.gittoy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gittoy.dataobject.MembershipInfo;
/**
 * MembershipRepositoryTest
 * Create By lzhao 2018/04/07
 */
public interface MembershipRepository extends JpaRepository<MembershipInfo, String>{
	List<MembershipInfo> findByOpenid(String openid);
}
