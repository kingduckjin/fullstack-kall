package com.project.kall.controller;

import com.project.kall.dto.OrderDTO;
import com.project.kall.dto.OrderDetailDTO;
import com.project.kall.dto.ProductDTO;
import com.project.kall.service.UserOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class UserOrderController {

    private final UserOrderService userOrderService;

    //    ★★★★★★★ Order ★★★★★★★
    @GetMapping("/user/{user_id}") //해당유저의 주문리스트 ok
    public List<OrderDTO> userFindAll(@PathVariable("user_id") String userId){
        return userOrderService.userFindAll(userId);
    }

    @GetMapping("/{id}") // 선택한 주문 가져옴 ok
    public OrderDTO userFindById(@PathVariable("id") Integer orderId){
        return userOrderService.userFindById(orderId);
    }

}
