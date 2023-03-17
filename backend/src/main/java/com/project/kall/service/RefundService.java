package com.project.kall.service;

import com.project.kall.dto.OrderDetailDTO;
import com.project.kall.dto.ProductDTO;
import com.project.kall.dto.RefundDTO;
import com.project.kall.entity.OrderDetailEntity;
import com.project.kall.entity.ProductEntity;
import com.project.kall.entity.RefundEntity;
import com.project.kall.repository.OrderDetailRepository;
import com.project.kall.repository.ProductRepository;
import com.project.kall.repository.RefundRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RefundService {

    @Autowired
    RefundRepository refundRepository;
    @Autowired
    OrderDetailRepository orderDetailRepository;
    @Autowired
    ProductRepository productRepository;

//    -----------------------------------주문 전체 목록 --------------------------------------------------------
    public List<RefundDTO> findAll() {
        List<RefundEntity> refundEntityList = refundRepository.findAllByOrderByRefundIdDesc();
        List<RefundDTO> refundDTOList = new ArrayList<>();
        for(RefundEntity refundEntity : refundEntityList) {
            if(refundEntity != null) {
                System.out.println(refundEntity);
                refundDTOList.add(RefundDTO.toRefundDTO(refundEntity));
            }
        }
        return refundDTOList;
    }

//    ----------------------------------- 반품 상세 내역 ---------------------------------------------------------
    //-> 개별 반품 요청  ==> refunds id에 따른 정보
    public RefundDTO rfList(Integer id) {
       Optional<RefundEntity> optionalrefundEntity = refundRepository.findById(id);
       if(optionalrefundEntity.isPresent()) {
           RefundEntity refundEntity = optionalrefundEntity.get();
           RefundDTO refundDTO = RefundDTO.toRefundDTO(refundEntity);
           return refundDTO;
       } else return null;
    }

    // ->개별 주문 상품 가져옴
    public OrderDetailDTO rfOrderD(Integer id) {
       RefundDTO refundDTO = rfList(id);
       Optional<OrderDetailEntity> optionalOrderDetailEntity = orderDetailRepository.findByOrderDnb(refundDTO.getOrderDetailDTO().getOrderDnb());
        if(optionalOrderDetailEntity.isPresent()) {
            OrderDetailEntity orderDetailEntity = optionalOrderDetailEntity.get();
            OrderDetailDTO orderDetailDTO = OrderDetailDTO.toOrderDetailDTO(orderDetailEntity);
            
            // 아래는 반품 시 각 product에 해당하는 amount 증가하는거 (주문제작케이크가 아닐 시)
            Optional<ProductEntity> optionalProductEntity = productRepository.findById(orderDetailEntity.getProductEntity().getProductId());
            if(optionalProductEntity.isPresent()) {
                ProductEntity productEntity = optionalProductEntity.get();
                if(productEntity.getCategoryEntity().getCategoryId() != "custom") {
                    productEntity.setAmount(productEntity.getAmount()+orderDetailDTO.getAmount());
                    productRepository.save(productEntity);
                }
            }
            return orderDetailDTO;
        } else return null;
    }

    // ->개별 상품 가져옴
    public ProductDTO rfP(Integer id) {
        OrderDetailDTO orderDetailDTO = rfOrderD(id);
        Optional<ProductEntity> optionalProductEntity = productRepository.findById(orderDetailDTO.getProductDTO().getProductId());
        if(optionalProductEntity.isPresent()) {
            ProductEntity productEntity = optionalProductEntity.get();
            ProductDTO productDTO = ProductDTO.toProductDTO(productEntity);
            return productDTO;
        } else return null;
    }

//    ----------------------------------- 반품 수정 ---------------------------------------------------------
    public void rfUpdate( RefundDTO refundDTO) {
//        Optional<RefundEntity> optionalRefundEntity = refundRepository.findById(id);
//        if(optionalRefundEntity.isPresent()) {
//            RefundEntity refundEntity = optionalRefundEntity.get();
//
//            System.out.println("refundDTO = " + refundDTO);
//            System.out.println("title" + refundDTO.getTitle());
//
//            refundEntity.toSetRefundEntity(refundDTO);
//
//            System.out.println("id" + refundEntity.getTitle());
//            System.out.println("...여기까진 되느건가");

            refundRepository.save(RefundEntity.toSetRefundEntity(refundDTO));
    }
}


