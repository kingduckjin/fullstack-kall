package com.project.kall.controller;

import com.project.kall.dto.OrderDetailDTO;
import com.project.kall.service.UserOrderDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orderdetails")
@RequiredArgsConstructor
public class UserOrderDetailController {

    private final UserOrderDetailService userOrderDetailService;

    @GetMapping("/order/{orderId}") // 해당 orderID
    public List<OrderDetailDTO> findOrderList(@PathVariable("orderId") Integer orderId){
        return userOrderDetailService.findOrderList(orderId);
    }

    @GetMapping("/{id}") // 해당 orderDetail {id} 조회
    public OrderDetailDTO findByIdOrderdetail(@PathVariable("id") Integer orderDNb){
        return userOrderDetailService.findOrderDetail(orderDNb);
    }
}
