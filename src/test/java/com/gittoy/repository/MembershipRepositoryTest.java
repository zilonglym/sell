package com.gittoy.repository;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.gittoy.dataobject.MembershipInfo;

/**
 * MembershipRepositoryTest
 * Create By lzhao 2018/04/07
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MembershipRepositoryTest {

	@Autowired
	private MembershipRepository membershipRepository;
	
	@Test
	public void savetest(){
		MembershipInfo membershipinfo = new MembershipInfo();
		membershipinfo.setId("1");
		membershipinfo.setUsername("子龙");
		membershipinfo.setOpenid("openid123");
		membershipinfo.setPurchaseAmount(new BigDecimal(120.12));
		membershipinfo.setScore(new BigDecimal(20.12));
		membershipinfo.setScoreGrade(1);
		MembershipInfo result = membershipRepository.save(membershipinfo);
		Assert.assertNotNull(result);
	}
	
}
