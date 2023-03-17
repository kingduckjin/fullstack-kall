package com.project.kall.controller;

import com.project.kall.dto.*;
import com.project.kall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/admin/orders")
public class OrderController {

    @Autowired
    OrderService orderService;


//  -----------------------------------주문 전체 목록 _ 모든 문의 가져옴-----------------------------------------------------
    @GetMapping("")
    public List<OrderDTO> list() {
        return orderService.findAll();
    }

//  ---------------------------주문번호 상세 내역 _ 개별 문의 가져옴 ---------------------------------------------------------
    @GetMapping("/{id}")  // id = orderId
    public OrderDTO  detailList(@PathVariable Integer id) {
        return orderService.detailList(id);
    }

//  ------------------------------각 주문 상세보기 _ 해당 주문에 대한 상품 목록 ---------------------------------------------------------
    @GetMapping("/detail/{id}") // id = order_dnb
    public List<OrderDetailDTO> detail(@PathVariable Integer id) {
        System.out.println(id);
        return orderService.orderDfindAll(id);
    }

//    -----------------------------------주문 상세보기에서 수정 ---------------------------------------------------------

    @PutMapping ("/{id}") // id = orderId
    public OrderDTO update(@PathVariable Integer id, @RequestBody OrderDTO orderDTO) {
        System.out.println("orderDTO" + orderDTO.getOrderId());
        orderService.orderUpdate(id, orderDTO);
        return orderService.detailList(id);
    }

//    -----------------------------------주문 삭제---------------------------------------------------------

    // 일괄취소 => id = orderId
    @PostMapping("/{id}") // 여기서 삭제는 찐 삭제가 아니라 상태를 취소로 변경하고, refund에 일부 내용 전달하는_ id=order_id
    // --> join 컬럼 내용 가져와서 저장하는거 대신 order table에서 조회해서 저장하는 형식으로 변경
    public List<OrderDTO> delete(@PathVariable Integer id) throws IOException {
        System.out.println(id);
        orderService.orderDelete(id); // id = order_id (배송주문 id그거)
        return orderService.findAll();
    }

    // 개별삭제 -> order_dnb 기준으로 삭제되는!!!
    @PostMapping("/pdelete/{id}") // id = order_dnb
    public void pdelete(@PathVariable Integer id) throws IOException  {
        System.out.println(id);

        orderService.orderPDelete(id);
    }

}