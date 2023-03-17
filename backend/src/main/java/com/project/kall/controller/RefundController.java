package com.project.kall.controller;

import com.project.kall.dto.OrderDetailDTO;
import com.project.kall.dto.ProductDTO;
import com.project.kall.dto.RefundDTO;
import com.project.kall.service.RefundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/refunds")
public class RefundController {

    @Autowired
    RefundService refundService;

//    ------------------------주문 전체 목록 _ 모든 반품 가져옴 -------------------------------------------------------------
    @RequestMapping("")
    public List<RefundDTO> list() {return refundService.findAll();}

//    ----------------------------------- 반품 상세 내역 _ 개별 반품 요청---------------------------------------------------------
    @GetMapping("/{id}") //-> 개별 반품 요청  ==> refundId 에 따른 정보
    public RefundDTO rfList(@PathVariable Integer id) {
        return refundService.rfList(id);
    }

//    @GetMapping("/orderdetails/{id}") // ->개별 주문 상품 가져옴 -> 주문상세번호 order_dnb 기준으로 refund id가 같은것들
//    public OrderDetailDTO rfOrderD(@PathVariable Integer id) {
//        return refundService.rfOrderD(id);
//    }
//
//    @GetMapping("/products/{id}") // ->개별 상품 가져옴 -> 위의 개별 반품에 해당하는 상품의 정보를 넘겨주기 id = order_dnb-> 사진 별점 등
//    public ProductDTO rfProduct(@PathVariable Integer id) {
//        return refundService.rfP(id);
//    }

//    --------------------------------- 반품 수정 _ 개별 반품 수정 ---------------------------------------------------------

    @PutMapping("/{id}") //-> 개별 반품 요청  ==> id = refundId에 따른 정보
    public RefundDTO rfUpdate(@PathVariable Integer id, @RequestBody RefundDTO refundDTO) {
        refundService.rfUpdate(refundDTO);
        return refundService.rfList(id);
    }

}
