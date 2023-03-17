package com.project.kall.controller;

import com.project.kall.dto.OrderDTO;
import com.project.kall.dto.OrderDetailDTO;
import com.project.kall.service.CartService;
import com.project.kall.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderControllerC {

    @Autowired
    OrderService orderService;
    @Autowired
    CartService cartService;
//    -----------------------------------주문 완료---------------------------------------------------------
// 주문상세 저장
// 주문하기를 클릭하면 선택한 상품을 로컬 스토리지에 임시로 담아서 리스팅하고 결제가 완료 되면 그때 데이터를 백엔드로 넘겨줌

    @PostMapping("") // order에서 user_id 찾아서 아래에 넣어주는
    public String order(@RequestBody OrderDTO orderDTO) {
        return orderService.orderId(orderDTO);
    }

    // 장바구니에서 결제하는 경우..? => 위에서 user_id를 전달해줌
    @PostMapping("/details/{id}") // id = userId
    public void list(@RequestBody List<OrderDetailDTO> orderDetailDTOList, @PathVariable String id) {
        List<OrderDetailDTO> orderDetailDTO = orderService.orderDetail(orderDetailDTOList);
        for(OrderDetailDTO orderDetail : orderDetailDTO) {
            if (orderDetail != null) {
                cartService.deleteCart(orderDetail, id);
            }
        }
    } // cart에서 userid랑 product 코드로 삭제

    @PostMapping("/directbuy") //=> 바로 결제하는 경우 "" 로 배송정보 받고 > orderDetail 받으면
    public void buyProduct(@RequestBody OrderDetailDTO orderDetailDTO) {
        if(orderDetailDTO != null) {
            orderService.buyProdouct(orderDetailDTO);
        };
    }


//    ---------------------------------유저 별 주문 리스트_마이페이지 메인에 해당----------------------------------------------
    @GetMapping("/user/{id}") // => 유저의 주문 갯수 하려고 만들었는데, 마이페이지에서 사용해도 됨
    public List<OrderDTO> orderDTOList(@PathVariable String id) {
        return orderService.orderList(id);
    }


////    ---------------------------------유저 별 주문 리스트----------------------------------------------------------
//    @GetMapping("/{id}") // => 개별 주문 / order_id로 detail 보는 듯
//    public List<OrderDTO> userOrder(@PathVariable
//    String id) {
//        return orderService.userorder(id);
//    }


}
