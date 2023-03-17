package com.project.kall.service;

import com.project.kall.controller.RefundController;
import com.project.kall.dto.*;
import com.project.kall.entity.OrderDetailEntity;
import com.project.kall.entity.OrderEntity;
import com.project.kall.entity.ProductEntity;
import com.project.kall.entity.RefundEntity;
import com.project.kall.repository.OrderDetailRepository;
import com.project.kall.repository.OrderRepository;
import com.project.kall.repository.ProductRepository;
import com.project.kall.repository.RefundRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final RefundRepository refundRepository;
    private final RefundController refundController;

    @Autowired
    ProductRepository productRepository;
    @Autowired
    CartService cartService;

    //    -----------------------------------주문 전체 목록 --------------------------------------------------------
    public List<OrderDTO> findAll() {
        List<OrderEntity> orderEntityList = orderRepository.findAllByOrderByOrderIdDesc();
        List<OrderDTO> orderDTOList = new ArrayList<>();
        for (OrderEntity orderEntity : orderEntityList) {
            if (orderEntity != null) {
                orderDTOList.add(OrderDTO.toOrderDTO(orderEntity));
            }
        }
        return orderDTOList;
    }
// => join으로 전체 뽑은거
//    public List<OrderDetailEntity> detailList(Integer id) {
//        List<Object[]> resultList = orderRepository.detailList(id);
//        List<OrderDetailEntity> orderDetailList = new ArrayList<>();
//        for (Object[] result : resultList) {
//            OrderDetailEntity orderDetail = (OrderDetailEntity) result[0];
//            OrderEntity order = (OrderEntity) result[1];
//            orderDetail.setOrderEntity(order);
//            orderDetailList.add(orderDetail);
//        }
//        return orderDetailList;
//    }

    //    -----------------------------------주문번호 상세 내역 ---------------------------------------------------------
//    -----------------------------------주문 상세보기에서 수정 ---------------------------------------------------------
    public OrderDTO detailList(Integer id) {
        Optional<OrderEntity> optionalOrderEntity = orderRepository.findById(id);
        if (optionalOrderEntity.isPresent()) {
            OrderEntity orderEntity = optionalOrderEntity.get();
            OrderDTO orderDTO = OrderDTO.toOrderDTO(orderEntity);
            return orderDTO;
        } else {
            return null;
        }
    }

    public void orderUpdate(Integer id, OrderDTO orderDTO) {
//        Optional<OrderEntity> OporderEntity = orderRepository.findById(id);
//        if(OporderEntity != null) {
//            OrderEntity orderEntity = OporderEntity.get();
//            orderEntity.toSetOrderEntity(orderDTO);
//            System.out.println("orderentity ::::" + orderEntity.getOrderId());
//            orderRepository.save(orderEntity);
//        }
        System.out.println(orderDTO.getOrderId());
        System.out.println(orderDTO);
        orderRepository.save(OrderEntity.toSetOrderEntity(orderDTO));

    }

    //   -----------------------------------각 주문 상세보기 ---------------------------------------------------------
    public List<OrderDetailDTO> orderDfindAll(Integer id) {
        List<OrderDetailEntity> orderDetailEntities = orderDetailRepository.findOrderId(id);
        System.out.println("orderDetailDTO 구하러 옴");
        List<OrderDetailDTO> orderDetailDTO = new ArrayList<>();
        for (OrderDetailEntity orderDetailEntity : orderDetailEntities) {
            if (orderDetailEntity != null) {
                orderDetailDTO.add(OrderDetailDTO.toOrderDetailDTO(orderDetailEntity));
            }
        }
        return orderDetailDTO;
    }
    //    -----------------------------------주문 취소---------------------------------------------------------
    public void orderDelete(Integer id) {  // 배송할 때 그 order_id
        Optional<OrderEntity> optionalOrderEntity = orderRepository.findById(id); // 한 배송지에 묶인 주문건들 넣어짐
        List<OrderEntity> orderEntityL = new ArrayList<>();
        if (optionalOrderEntity.isPresent()) {
            OrderEntity orderEntity = optionalOrderEntity.get();
            orderEntity.setOrder_status("주문취소");
            orderRepository.save(orderEntity);
            orderEntityL.add(orderEntity);


            for (OrderEntity oEntity : orderEntityL) {
                List<OrderDetailDTO> orderDetailDTO = orderDfindAll(oEntity.getOrderId());
                List<RefundDTO> refundDTOs = refundController.list();
                List<OrderDetailDTO> orderList = new ArrayList<>();


                for (OrderDetailDTO orderDetail : orderDetailDTO) {
                    Boolean flag = true;
                    for (RefundDTO refund : refundDTOs) {   // 주문상세번호가 같은게 추가되지 않게 수정 (고유번호)
                        if (orderDetail.getOrderDnb().equals(refund.getOrderDetailDTO().getOrderDnb())) {
                            flag = false;
                            return;
                        }
                    }
                    if (flag) { // refund에 없으면 -> detail status 처리 / refund 처리 , 삽입
                        orderDetail.setStatus("주문취소");
                        OrderDetailEntity orderDetailEntity = OrderDetailEntity.toSetOrderDetailEntity(orderDetail);
                        orderDetailRepository.save(orderDetailEntity);
                        RefundDTO refundDTO = new RefundDTO();
                        refundDTO.setOrderDetailDTO(orderDetail);
                        refundDTO.setUserDTO(orderDetail.getOrderDTO().getUserDTO());
                        refundDTO.setTitle("관리자 취소처리");
                        refundDTO.setContent("관리자 취소처리");
                        refundDTO.setStatus("반품확정");
                        refundRepository.save(RefundEntity.toSetRefundEntity(refundDTO));

                        // 아래는 반품 시 각 product에 해당하는 amount 증가하는거 (주문제작케이크가 아닐 시)
                        Optional<ProductEntity> optionalProductEntity = productRepository.findById(orderDetailEntity.getProductEntity().getProductId());
                        if (optionalProductEntity.isPresent()) {
                            ProductEntity productEntity = optionalProductEntity.get();
                            if (productEntity.getCategoryEntity().getCategoryId() != "custom") {
                                productEntity.setAmount(productEntity.getAmount() + orderDetail.getAmount());
                                productRepository.save(productEntity);
                            }
                        }
                    }
                }
            }
        }
    }

    // 개별 취소----------------------------------------------------------------------------------------
    public void orderPDelete(Integer id) {
        Optional<OrderDetailEntity> optionalOrderDetailEntity = orderDetailRepository.findById(id); // order_dnb 받음
        if (optionalOrderDetailEntity.isPresent()) {
            OrderDetailEntity orderDetailEntity = optionalOrderDetailEntity.get();
            orderDetailEntity.setStatus("주문취소");
            orderDetailRepository.save(orderDetailEntity);

            OrderDetailDTO orderDetailDTO =  OrderDetailDTO.toOrderDetailDTO(orderDetailEntity);
            //  상태명 변경 -> orderdetail에서 변경되는것

            List<RefundDTO> refundEntities = refundController.list(); // refund 받아옴
            RefundDTO refundDTO = new RefundDTO();

            boolean flag = true; // 이미 refund에 있는지 확인 코드 (T : 없음ㅁ / F : 있음)

            for (RefundDTO refund : refundEntities) { // refund만큼 for문 돌림 -> 취소 중복 데이터 있는지
                if (orderDetailDTO.getOrderDnb().equals(refund.getOrderDetailDTO().getOrderDnb())) {
                    flag = false;
                }
            }

            if (flag) { ///없음 -> refund에 삽입 / status 처리
                refundDTO.setUserDTO(orderDetailDTO.getOrderDTO().getUserDTO());
                refundDTO.setOrderDetailDTO(orderDetailDTO);
                refundDTO.setTitle("관리자 취소처리");
                refundDTO.setContent("관리자 취소처리");
                refundDTO.setStatus("반품확정");
                refundRepository.save(RefundEntity.toSetRefundEntity(refundDTO));

                // 아래는 반품 시 각 product에 해당하는 amount 증가하는거 (주문제작케이크가 아닐 시)
                Optional<ProductEntity> optionalProductEntity = productRepository.findById(orderDetailEntity.getProductEntity().getProductId());
                if (optionalProductEntity.isPresent()) {
                    ProductEntity productEntity = optionalProductEntity.get();
                    if (productEntity.getCategoryEntity().getCategoryId() != "custom") {
                        productEntity.setAmount(productEntity.getAmount() + orderDetailDTO.getAmount());
                        productRepository.save(productEntity);
                    }
                }
            }
        }
    }

// 홈페이지 --------------------------------------------------------------------------------------------------------------
//    -----------------------------------주문 완료---------------------------------------------------------

    public String orderId(OrderDTO orderDTO) {
        OrderEntity orderEntity = OrderEntity.toSetOrderEntity(orderDTO);
        orderRepository.save(orderEntity);
        String userId = orderDTO.getUserDTO().getUserId();
        return userId;
    }

    //    public List<OrderDetailDTO> orderDetail(List<OrderDetailDTO> orderDetailDTOList) {
    public List<OrderDetailDTO> orderDetail(List<OrderDetailDTO> orderDetailDTOList) {
        List<OrderDetailDTO> orderDetailDTOL = new ArrayList<>();
        for(OrderDetailDTO orderDetailDTO : orderDetailDTOList ) {
            if(orderDetailDTO != null) {
                orderDetailDTO.setStatus("결제완료");
                orderDetailDTOL.add(orderDetailDTO);

                OrderDetailEntity orderDetailEntity = OrderDetailEntity.toSetOrderDetailEntity(orderDetailDTO);
                orderDetailRepository.save(orderDetailEntity);

                // 아래는 주문 시 각 product에 해당하는 amount 감소하는거
                Optional<ProductEntity> optionalProductEntity = productRepository.findById(orderDetailEntity.getProductEntity().getProductId());
                if(optionalProductEntity.isPresent()) {
                    ProductEntity productEntity = optionalProductEntity.get();
                    if(productEntity != null) {
                        productEntity.setAmount(productEntity.getAmount()-orderDetailDTO.getAmount());
                        System.out.println(productEntity.getAmount()-orderDetailDTO.getAmount());
                        productRepository.save(productEntity);
                    }
                }
            }
        }
        return orderDetailDTOL;
    }

    // id 안 넘겨줄 때
//    public List<OrderDetailDTO> orderDetail(List<OrderDetailDTO> orderDetailDTOList) {
//        List<OrderDetailDTO> orderDetailDTOL = new ArrayList<>();
//        String userId = null;
//        for(OrderDetailDTO orderDetailDTO : orderDetailDTOList ) {
//            if(orderDetailDTO != null) {
//                orderDetailDTO.setStatus("처리 중");
//                orderDetailDTOL.add(orderDetailDTO);
//
//                //   cart에 전달한 userid 찾기
//                userId = orderDetailDTO.getOrderDTO().getUserDTO().getUserId();
//
//                OrderDetailEntity orderDetailEntity = OrderDetailEntity.toSetOrderDetailEntity(orderDetailDTO);
//                orderDetailRepository.save(orderDetailEntity);
//            }
//        }
//        cartService.deleteCart(userId); // cart에 전달 , 삭제처리
//        return orderDetailDTOL;
//    }

    //    -----------------------------------유저 별 주문 리스트--------------------------------------------------------
    public List<OrderDTO> orderList(String id) {
        List<OrderEntity> orderEntityList = orderRepository.findOrderId(id);
        List<OrderDTO> orderDTOList = new ArrayList<>();
        for(OrderEntity orderEntity : orderEntityList) {
            if(orderEntity != null) {
                orderDTOList.add(OrderDTO.toOrderDTO(orderEntity));
            }
        }
        return orderDTOList;
    }

    public void buyProdouct(OrderDetailDTO orderDetailDTO) { // 바로 결제
        orderDetailDTO.setStatus("결제완료");
        orderDetailRepository.save(OrderDetailEntity.toSetOrderDetailEntity(orderDetailDTO));
        // 마일리지 (결제했으니까 상용과
    }

}

