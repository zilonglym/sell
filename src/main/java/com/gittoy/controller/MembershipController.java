package com.gittoy.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.gittoy.dataobject.MembershipClass;
import com.gittoy.dataobject.MembershipInfo;
import com.gittoy.dataobject.ScoreFunction;
import com.gittoy.exception.SellException;
import com.gittoy.form.ClassForm;
import com.gittoy.form.ScoreFunctionForm;
import com.gittoy.service.MembershipClassService;
import com.gittoy.service.MembershipService;
import com.gittoy.service.ScoreFunctionService;

import lombok.val;

/**
 * MembershipRepositoryTest
 * Create By lzhao 2018/04/07
 */
@Controller
@RequestMapping("/seller/membership")
public class MembershipController {
	
	@Autowired
	private MembershipClassService membershipClassService;

	@Autowired
	private MembershipService membershipService;
	
	@Autowired
	private ScoreFunctionService scoreFunctionService;
	
	/**
     * 会员列表
     * @param page 第几页，从第1页开始
     * @param size 一页有多少条数据
     * @return
     */
    @GetMapping("/membershiperlist")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "10") Integer size,
                             Map<String, Object> map) {
        PageRequest request = new PageRequest(page - 1, size);
        Page<MembershipInfo> membershipInfoPage = membershipService.findList(request);
        Page<MembershipClass> membershipClass = membershipClassService.findAll(request);
        map.put("membershipClassList", membershipClass.getContent());
        map.put("membershipInfoPage", membershipInfoPage);
        map.put("currentPage", page);
        map.put("size", size);
        return new ModelAndView("/membership/membershiperlist", map);
    }
    
    /**
     * 列表
     *
     * @param map
     * @return
     */
    @GetMapping("/scorefunctionlist")
    public ModelAndView list(Map<String, Object> map) {
        List<ScoreFunction> scoreFunctionList = scoreFunctionService.findAll();
        map.put("scoreFunctionList", scoreFunctionList);
        return new ModelAndView("membership/scorefunctionlist", map);
    }
    
   
    /**
     * 功能状态展示
     *
     * @param score
     * @param map
     * @return
     */
    @GetMapping("/stausindex")
    public ModelAndView index(@RequestParam(value = "score", required = false) String parametername,
                              Map<String, Object> map) {

        if (StringUtils.isNotEmpty(parametername)) {
        	ScoreFunction scoreFunctionInfo = scoreFunctionService.findOne(parametername);
            map.put("scoreFunctionInfo", scoreFunctionInfo);
        }

        return new ModelAndView("membership/stausindex", map);
    }
    
    /**
     * 保存/更新
     *
     * @param form
     * @param bindingResult
     * @param map
     * @return
     */
    @PostMapping("/save")
    public ModelAndView save(@Valid ScoreFunctionForm form,
                             BindingResult bindingResult,
                             Map<String, Object> map) {
        if (bindingResult.hasErrors()) {
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url", "/sell/seller/membership/scorefunctionlist");
            return new ModelAndView("common/error", map);
        }

        try {
        	ScoreFunction scoreFunctionInfo = new ScoreFunction();
        	form.setSellerName("zhihe");
            if (form.getSellerName() != null) {
            	scoreFunctionInfo = scoreFunctionService.findOne(form.getSellerName());
            }
            BeanUtils.copyProperties(form, scoreFunctionInfo);
            scoreFunctionService.save(scoreFunctionInfo);
        } catch (SellException e) {
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/membership/scorefunctionlist");
            return new ModelAndView("common/error", map);
        }

        map.put("url", "/sell/seller/membership/scorefunctionlist");
        return new ModelAndView("common/success", map);
    }
    
    /**
     * 分级列表
     *
     * @param map
     * @return
     */
    @GetMapping("/classlist")
    public ModelAndView classlist(Map<String, Object> map,
    		@RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {
    	Sort sort = new Sort(Direction.ASC, "scoreMin");
    	PageRequest request = new PageRequest(page - 1, size,sort);
    	Page<MembershipClass> allClass = membershipClassService.findAll(request);
        map.put("allClass", allClass.getContent());
        return new ModelAndView("membership/classlist", map);
    }
    
    /**
     * 修改分级
     *
     * @param score
     * @param map
     * @return
     */
    @GetMapping("/classindex")
    public ModelAndView classIndex(@RequestParam(value = "scoreClass", required = false) Integer scoreClass,
                              Map<String, Object> map) {
    	MembershipClass membershipClass = new MembershipClass();
    	if(scoreClass!=null){
    		membershipClass = membershipClassService.findByScoreClass(scoreClass);
    	}    	
    	map.put("membershipClass", membershipClass);
        return new ModelAndView("membership/classindex", map);
    }
    
    /**
     * 保存/更新分级
     *
     * @param form
     * @param bindingResult
     * @param map
     * @return
     */
    @PostMapping("/classsave")
    public ModelAndView classSave(@Valid ClassForm form,
                             BindingResult bindingResult,
                             Map<String, Object> map) {
        if (bindingResult.hasErrors()) {
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url", "/sell/seller/membership/classlist");
            return new ModelAndView("common/error", map);
        }

        try {
        	MembershipClass membershipClass = new MembershipClass();
        	if(null!=form.getScoreClass()){
        		membershipClass = membershipClassService.findByScoreClass(form.getScoreClass());
        		BeanUtils.copyProperties(form, membershipClass);
                membershipClassService.save(membershipClass);
        	}else{
        		membershipClass.setScoreClass(0);
        		membershipClass.setScoreMin(form.getScoreMin());
        		membershipClass.setScoreMax(form.getScoreMax());
        		membershipClass.setScoreDesc(form.getScoreDesc());
                membershipClassService.save(membershipClass);
        	}
        } catch (SellException e) {
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/membership/classlist");
            return new ModelAndView("common/error", map);
        }

        map.put("url", "/sell/seller/membership/classlist");
        return new ModelAndView("common/success", map);
    }
    
    
    /**
     * 删除分级
     *
     * @param form
     * @param bindingResult
     * @param map
     * @return
     */
    @GetMapping("/classdelete")
    public ModelAndView classDelete(@RequestParam(value = "scoreClass", required = true) Integer scoreClass,
            Map<String, Object> map) {
        try {
            membershipClassService.delete(scoreClass);
        } catch (SellException e) {
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/membership/classlist");
            return new ModelAndView("common/error", map);
        }

        map.put("url", "/sell/seller/membership/classlist");
        return new ModelAndView("common/success", map);
    }
    
    /**
     * 增加分级
     *
     * @param form
     * @param bindingResult
     * @param map
     * @return
     */
    @GetMapping("/classadd")
    public ModelAndView classAdd(Map<String, Object> map) {
        map.put("url", "/sell/seller/membership/classindex");
        return new ModelAndView("membership/classindex", map);
    }
   
}
