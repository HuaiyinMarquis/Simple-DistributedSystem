package com.imspa.resource.web;

import com.imspa.api.OrderRemoteService;
import com.imspa.api.order.OrderDTO;
import com.imspa.api.order.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * @author Pann
 * @Description TODO
 * @Date 2019-08-08 16:51
 */
@RestController
@RequestMapping("/resource")
public class ResourceController {
    @Autowired
    private OrderRemoteService orderRemoteService;

    @GetMapping("/get/{id}")
    public OrderVO get(@PathVariable("id")String id) {
        OrderDTO orderDTO = orderRemoteService.get(id);
        return new OrderVO().setOrderId(orderDTO.getOrderId()).setOrderPrice(orderDTO.getOrderPrice())
                .setProductId(orderDTO.getProductId()).setProductName(orderDTO.getProductName())
                .setStatus(orderDTO.getStatus()).setUserId(orderDTO.getUserId());
    }

    @GetMapping()
    public List<OrderVO> list() {
        return Arrays.asList(new OrderVO().setOrderId("1").setOrderPrice(new BigDecimal(2.3)).setProductName("西瓜"));
    }
}
