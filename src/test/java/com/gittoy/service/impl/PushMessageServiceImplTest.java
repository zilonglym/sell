package com.gittoy.service.impl;

import com.gittoy.dto.OrderDTO;
import com.gittoy.service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * PushMessageServiceImplTest
 * Create By GaoYu 2017/11/27 14:38
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PushMessageServiceImplTest {

    @Autowired
    private PushMessageServiceImpl pushMessageService;

    @Autowired
    private OrderService orderService;

    @Test
    public void orderStatus() throws Exception {
        OrderDTO orderDTO = orderService.findOne("1499097346204378750");
        pushMessageService.orderStatus(orderDTO);
    }
}