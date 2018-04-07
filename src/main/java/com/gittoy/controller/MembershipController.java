package com.gittoy.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.gittoy.dataobject.MembershipInfo;
import com.gittoy.service.MembershipService;

/**
 * MembershipRepositoryTest
 * Create By lzhao 2018/04/07
 */
@Controller
@RequestMapping("/seller/membership")
public class MembershipController {

	
	@Autowired
	private MembershipService membershipService;
	
	/**
     * 订单列表
     * @param page 第几页，从第1页开始
     * @param size 一页有多少条数据
     * @return
     */
    @GetMapping("list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "10") Integer size,
                             Map<String, Object> map) {
        PageRequest request = new PageRequest(page - 1, size);
        Page<MembershipInfo> membershipInfoPage = membershipService.findList(request);
        map.put("membershipInfoPage", membershipInfoPage);
        map.put("currentPage", page);
        map.put("size", size);
        return new ModelAndView("/membership/list", map);
    }
}
