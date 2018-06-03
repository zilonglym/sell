package com.gittoy.repository;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.gittoy.dataobject.MembershipClass;
/**
 * MembershipRepositoryTest
 * Create By lzhao 2018/04/07
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MembershipClassRepositoryTest {

	@Autowired
	private MembershipClassRepository membershipClassRepository;
	
//	@Test
//	public void savetest(){
//		MembershipClass membershipClass = new MembershipClass();
//		membershipClass.setSellId("zhihe");
//		membershipClass.setScoreClass("1");
//		membershipClass.setScoreMin(new BigDecimal(0));
//		membershipClass.setScoreMax(new BigDecimal(1000));
//		MembershipClass result = membershipClassRepository.save(membershipClass);
//		MembershipClass membershipClass2 = new MembershipClass();
//		membershipClass2.setSellId("zhihe");
//		membershipClass2.setScoreClass("2");
//		membershipClass2.setScoreMin(new BigDecimal(1000));
//		membershipClass2.setScoreMax(new BigDecimal(2000));
//		MembershipClass result2 = membershipClassRepository.save(membershipClass2);
//		MembershipClass membershipClass3 = new MembershipClass();
//		membershipClass3.setSellId("zhihe");
//		membershipClass3.setScoreClass("3");
//		membershipClass3.setScoreMin(new BigDecimal(2000));
//		membershipClass3.setScoreMax(new BigDecimal(10000000));
//		MembershipClass result3 = membershipClassRepository.save(membershipClass3);
//	}
}
