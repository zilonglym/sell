package com.gittoy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gittoy.dataobject.MembershipClass;

public interface MembershipClassRepository extends JpaRepository<MembershipClass, Integer> {
	
	MembershipClass findByScoreClass(Integer scoreClass);
	
}
