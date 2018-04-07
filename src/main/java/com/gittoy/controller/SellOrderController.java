package com.gittoy.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.gittoy.dataobject.MembershipClass;
import com.gittoy.dataobject.MembershipInfo;
import com.gittoy.dto.OrderDTO;
import com.gittoy.enums.ResultEnum;
import com.gittoy.exception.SellException;
import com.gittoy.service.MembershipClassService;
import com.gittoy.service.MembershipService;
import com.gittoy.service.OrderService;

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
                             @RequestParam(value = "size", defaultValue = "10") Integer size,
                             Map<String, Object> map) {
        PageRequest request = new PageRequest(page - 1, size);
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
}
