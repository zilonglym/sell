package com.gittoy.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.naming.java.javaURLContextFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageInfo;
import com.gittoy.dataobject.MembershipClass;
import com.gittoy.dataobject.MembershipInfo;
import com.gittoy.dataobject.OrderDetail;
import com.gittoy.dto.OrderDTO;
import com.gittoy.enums.ResultEnum;
import com.gittoy.exception.SellException;
import com.gittoy.service.MembershipClassService;
import com.gittoy.service.MembershipService;
import com.gittoy.service.OrderService;
import com.gittoy.vo.SalesQueryVo;

import lombok.extern.slf4j.Slf4j;

/**
 * 卖家端订单
 *
 * Create By GaoYu 2017/11/22 10:35
 */
@Controller
@RequestMapping("/seller/order")
@Slf4j
public class SellOrderController {

    @Autowired
    private OrderService orderService;
    
    @Autowired
    private MembershipService membershipService;

    @Autowired
    private MembershipClassService membershipClassService;
    
    /**
     * 订单列表
     * @param page 第几页，从第1页开始
     * @param size 一页有多少条数据
     * @return
     */
    @GetMapping("list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "30") Integer size,
                             Map<String, Object> map) {
    	Sort sort = new Sort(Direction.DESC, "createTime");
    	PageRequest request = new PageRequest(page - 1, size,sort);
        Page<OrderDTO> orderDTOPage = orderService.findList(request);
        map.put("orderDTOPage", orderDTOPage);
        map.put("currentPage", page);
        map.put("size", size);
        return new ModelAndView("/order/list", map);
    }

    /**
     * 取消订单
     *
     * @param orderId
     * @return
     */
    @GetMapping("/cancel")
    public ModelAndView cancel(@RequestParam("orderId") String orderId,
                               Map<String, Object> map) {
        try {
            OrderDTO orderDTO = orderService.findOne(orderId);
            orderService.cancel(orderDTO);
        } catch (SellException e) {
            log.error("【卖家端取消订单】发生异常{}", e);
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/order/list");
            return new ModelAndView("common/error", map);
        }

        map.put("msg", ResultEnum.ORDER_CANCEL_SUCCESS.getMessage());
        map.put("url", "/sell/seller/order/list");
        return new ModelAndView("common/success");
    }

    /**
     * 订单详情
     *
     * @param orderId
     * @param map
     * @return
     */
    @GetMapping("/detail")
    public ModelAndView detail(@RequestParam("orderId") String orderId,
                               Map<String, Object> map) {

        OrderDTO orderDTO = new OrderDTO();

        try {
            orderDTO = orderService.findOne(orderId);
        } catch (SellException e) {
            log.error("【卖家端查询订单详情】发生异常{}", e);
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/order/list");
            return new ModelAndView("common/error", map);
        }

        map.put("orderDTO", orderDTO);
        return new ModelAndView("order/detail", map);
    }

    /**
     * 完结订单
     *
     * @param orderId
     * @param map
     * @return
     */
    @GetMapping("/finish")
    public ModelAndView finished(@RequestParam("orderId") String orderId,
                                 Map<String, Object> map) {

        try {
            OrderDTO orderDTO = orderService.findOne(orderId);
            orderService.finish(orderDTO);
            
            List<MembershipClass> membershipClassList = membershipClassService.findAll("zhihe");
            int scoreGrade = 1;
            
            //查询是否已存在消费记录
            MembershipInfo findedMembershipInfo = membershipService.findOne(orderDTO.getBuyerOpenid());
            if(findedMembershipInfo==null){
            	//不存在则直接添加
            	scoreGrade = getScoreGrade(membershipClassList, orderDTO.getOrderAmount());
            	
            	MembershipInfo membershipinfo = new MembershipInfo();
                membershipinfo.setId("1");
                membershipinfo.setUsername(orderDTO.getBuyerName());
                membershipinfo.setOpenid(orderDTO.getBuyerOpenid());
                membershipinfo.setPurchaseAmount(orderDTO.getOrderAmount());
                membershipinfo.setScore(orderDTO.getOrderAmount());
                membershipinfo.setScoreGrade(scoreGrade);
                membershipService.save(membershipinfo);
            }else{
            	//存在则需要更新原记录
            	BigDecimal toUpdate = orderDTO.getOrderAmount().add(findedMembershipInfo.getPurchaseAmount());
            	scoreGrade = getScoreGrade(membershipClassList, toUpdate);
            	
            	MembershipInfo membershipinfo = new MembershipInfo();
                membershipinfo.setId("1");
                membershipinfo.setUsername(orderDTO.getBuyerName());
                membershipinfo.setOpenid(orderDTO.getBuyerOpenid());
                membershipinfo.setPurchaseAmount(toUpdate);
                membershipinfo.setScore(findedMembershipInfo.getScore().add(orderDTO.getOrderAmount()));
                membershipinfo.setScoreGrade(scoreGrade);
            	membershipService.save(membershipinfo);
            }
        } catch (SellException e) {
            log.error("【卖家端完结订单】发生异常{}", e);
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/order/list");
            return new ModelAndView("common/error", map);
        }

        map.put("msg", ResultEnum.ORDER_FINISH_SUCCESS.getMessage());
        map.put("url", "/sell/seller/order/list");
        return new ModelAndView("common/success");
    }
    
    /**
     * 更新会员等级
     * @param membershipClassList
     * @param input
     * @return
     */
    public static int getScoreGrade(List<MembershipClass> membershipClassList, BigDecimal input){
    	int scoreGrade = 1;
    	for(MembershipClass membershipClass : membershipClassList){
        	if(input.compareTo(membershipClass.getScoreMin())>=0
        		&& membershipClass.getScoreMax().compareTo(input)>0){
        		scoreGrade = Integer.parseInt(membershipClass.getScoreClass());
        		break;
        	}
        }
    	return scoreGrade;
    }
    
    @RequestMapping(value = "/flow/index")
    public ModelAndView index() {
        ModelAndView view = new ModelAndView("salesflow/index");
        return view;
    }
    
    @ResponseBody
    @RequestMapping(value = "/flow/getSalesFlowList", method = RequestMethod.POST)
    public Map<String, Object> getList(@RequestBody SalesQueryVo queryVo) {

        Map<String, Object> result = new HashMap<>();
        result.put("total", 0);
        result.put("rows", new ArrayList());

        // 参数验证
        if (null == queryVo) {
            return result;
        }
        try {
        	if(StringUtils.isEmpty(queryVo.getCategoryName()) && StringUtils.isEmpty(queryVo.getOrderId()) && StringUtils.isEmpty(queryVo.getStartDate()) && StringUtils.isEmpty(queryVo.getEndDate())){
        		PageInfo<OrderDetail> pageData = orderService.findAllSalesList(queryVo);
                if (pageData != null) {
                    result.put("total", pageData.getTotal());
                    result.put("rows", pageData.getList());
                }
                return result;
        	}
        	if(StringUtils.isEmpty(queryVo.getStartDate())){
        		queryVo.setStartDate(new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date(118, 3, 12)));
        	}
        	if(StringUtils.isEmpty(queryVo.getEndDate())){
        		queryVo.setEndDate(new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date(118, 5, 12)));
        	}
            PageInfo<OrderDetail> pageData = orderService.findSalesListByOpenId(queryVo);
            if (pageData != null) {
                result.put("total", pageData.getTotal());
                result.put("rows", pageData.getList());
            }
        } catch (Exception e) {
            log.error("获取销售流水查询配置异常 e:{}" + e.getMessage(), e);
        }
        return result;
    }
}
