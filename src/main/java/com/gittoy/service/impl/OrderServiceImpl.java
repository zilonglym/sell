package com.gittoy.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gittoy.converter.OrderMaster2OrderDTOConverter;
import com.gittoy.dataobject.OrderDetail;
import com.gittoy.dataobject.OrderMaster;
import com.gittoy.dataobject.ProductInfo;
import com.gittoy.dto.CartDTO;
import com.gittoy.dto.OrderDTO;
import com.gittoy.enums.OrderStatusEnum;
import com.gittoy.enums.PayStatusEnum;
import com.gittoy.enums.ResultEnum;
import com.gittoy.exception.SellException;
import com.gittoy.repository.OrderDetailRepository;
import com.gittoy.repository.OrderMasterRepository;
import com.gittoy.service.OrderService;
import com.gittoy.service.PayService;
import com.gittoy.service.ProductService;
import com.gittoy.service.WebSocket;
import com.gittoy.utils.KeyUtil;
import com.gittoy.vo.SalesQueryVo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

/**
 * OrderServiceImpl
 * Create By GaoYu 2017/11/15 13:10
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Autowired
    private PayService payService;

    @Autowired
    private PushMessageServiceImpl pushMessageService;

    @Autowired
    private WebSocket webSocket;

    /**
     * 创建订单
     *
     * @param orderDTO
     */
    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {

        String orderId = KeyUtil.genUniqueKey();
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);

        // 1. 查询商品（数量，价格）
        for (OrderDetail orderDetail : orderDTO.getOrderDetailList()) {
            ProductInfo productInfo = productService.findOne(orderDetail.getProductId());
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIT);
                // throw new ResponseBankException();
            }

            // 2. 计算订单总价
            orderAmount = productInfo.getProductPrice()
                    .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                    .add(orderAmount);

            // 订单详情入库
            orderDetail.setDetailId(KeyUtil.genUniqueKey());
            orderDetail.setOrderId(orderId);
            BeanUtils.copyProperties(productInfo, orderDetail);
            orderDetailRepository.save(orderDetail);
        }

        // 3. 写入订单数据库（orderMaster和orderDetail）
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderDeliveryAmount(orderDTO.getOrderDeliveryAmount());
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMasterRepository.save(orderMaster);

        // 4. 扣库存
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream().map(e ->
                new CartDTO(e.getProductId(), e.getProductQuantity())
        ).collect(Collectors.toList());
        productService.decreaseStock(cartDTOList);

        // 5.发送websocket消息
        webSocket.sendMessage(orderDTO.getOrderId());

        return orderDTO;
    }

    /**
     * 查询单个订单
     *
     * @param orderId
     */
    @Override
    public OrderDTO findOne(String orderId) {
        OrderMaster orderMaster = orderMasterRepository.findOne(orderId);
        if (orderMaster == null) {
            throw new SellException(ResultEnum.ORDER_NOT_EXIT);
        }
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
        if (CollectionUtils.isEmpty(orderDetailList)) {
            throw new SellException(ResultEnum.ORDER_DETAIL_NOT_EXIST);
        }
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);

        return orderDTO;
    }

    /**
     * 查询订单列表
     *
     * @param buyerOpenid
     * @param pageable
     */
    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findByBuyerOpenid(buyerOpenid, pageable);

        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());

        return new PageImpl<OrderDTO>(orderDTOList, pageable, orderMasterPage.getTotalElements());
    }

    /**
     * 取消订单
     *
     * @param orderDTO
     */
    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {

        OrderMaster orderMaster = new OrderMaster();

        // 判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("【取消订单】订单状态不正确，orderId={}，orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        // 修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if (updateResult == null) {
            log.error("【取消订单】更新失败，orderMaster={}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }

        // 返回库存
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            log.error("【取消订单】订单中无商品详情，orderDTO={}", orderDTO);
            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream()
                .map(e -> new CartDTO(e.getProductId(), e.getProductQuantity()))
                .collect(Collectors.toList());
        productService.increaseStock(cartDTOList);

        // 如果已支付，需要退款
        if (orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())) {
            payService.refund(orderDTO);
        }

        return orderDTO;
    }

    /**
     * 完结订单
     *
     * @param orderDTO
     */
    @Override
    @Transactional
    public OrderDTO finish(OrderDTO orderDTO) {
        // 判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("【完结订单】订单状态不正确，orderId={}, orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        
        // 判断订单状态
        if (!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())) {
            log.error("【完结订单】订单支付状态不正确，orderId={}, orderPayStatus={}", orderDTO.getOrderId(), orderDTO.getPayStatus());
            throw new SellException(ResultEnum.ORDER_PAY_WAIT_ERROR);
        }

        // 修改状态
        orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if (updateResult == null) {
            log.error("【完结订单】更新失败，orderMaster={}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }

        // 推送微信模板消息
        pushMessageService.orderStatus(orderDTO);

        return orderDTO;
    }

    /**
     * 支付订单
     *
     * @param orderDTO
     */
    @Override
    @Transactional
    public OrderDTO paid(OrderDTO orderDTO) {
        // 判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("【订单支付成功】订单状态不正确，orderId={}, orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        // 判断支付状态
        if (!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())) {
            log.error("【订单支付完成】订单支付状态不正确，orderDTO={}", orderDTO);
            throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }

        // 修改支付状态
        orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if (updateResult == null) {
            log.error("【订单支付完成】更新失败，orderMaster={}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }

        return orderDTO;
    }

    /**
     * 查询订单列表：所有
     *
     * @param pageable
     */
    @Override
    public Page<OrderDTO> findList(Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findAll(pageable);
        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());
        return new PageImpl<>(orderDTOList, pageable, orderMasterPage.getTotalElements());
    }

	@Override
	public PageInfo<OrderDetail> findSalesList(SalesQueryVo queryVo) {
		int pageNumber = queryVo.getPageNumber();
        int pageSize = queryVo.getLimit();
        PageHelper.startPage(pageNumber, pageSize);
        List<OrderDetail> list = orderDetailRepository.findByOrderIdAndCreateTimeBetween(queryVo.getOrderId(),queryVo.getStartDate(),queryVo.getEndDate());
        if (null == list || list.size() == 0) {
            log.error("获取销售流水分页信息失败");
            return null;
        }
        return new PageInfo<>(list);
	}

	@Override
	public PageInfo<OrderDetail> findSalesListByOpenId(SalesQueryVo queryVo) {
		int pageNumber = queryVo.getPageNumber();
        int pageSize = queryVo.getLimit();
        PageHelper.startPage(pageNumber, pageSize);
        List<OrderDetail> list = orderDetailRepository.findByOrderId(queryVo.getOrderId());
        if (null == list || list.size() == 0) {
            log.error("获取销售流水分页信息失败  in findSalesListByOpenId");
            return null;
        }
        return new PageInfo<>(list);
	}

	@Override
	public PageInfo<OrderDetail> findAllSalesList(SalesQueryVo queryVo) {
		int pageNumber = queryVo.getPageNumber();
        int pageSize = queryVo.getLimit();
        PageHelper.startPage(pageNumber, pageSize);
        List<OrderDetail> list = orderDetailRepository.findAll();
        if (null == list || list.size() == 0) {
            log.error("获取销售流水分页信息失败");
            return null;
        }
        return new PageInfo<>(list);
	}
}
