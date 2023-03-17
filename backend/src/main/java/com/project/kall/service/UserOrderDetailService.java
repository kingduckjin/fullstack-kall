package com.project.kall.service;


import com.project.kall.dto.OrderDetailDTO;
import com.project.kall.entity.OrderDetailEntity;
import com.project.kall.repository.OrderDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserOrderDetailService {
    private final OrderDetailRepository orderDetailRepository;

    public List<OrderDetailDTO> findOrderList(Integer orderId) {
        List<OrderDetailEntity> orderDetailEntities = orderDetailRepository.findAll();
        List<OrderDetailDTO> orderDetailDTOList = new ArrayList<>();
        for (OrderDetailEntity orderList : orderDetailEntities) {
            if (orderId == orderList.getOrderEntity().getOrderId()) {
                System.out.println(orderList.getOrderEntity().getOrderId());
                orderDetailDTOList.add(OrderDetailDTO.toOrderDetailDTO(orderList));
            }
        }
        return orderDetailDTOList;
    }

    public OrderDetailDTO findOrderDetail(Integer orderDNb) {
        Optional<OrderDetailEntity> orderDetailEntity = orderDetailRepository.findById(orderDNb);
        OrderDetailEntity orderDetail = new OrderDetailEntity();
        if (orderDetailEntity.isPresent()) {
            orderDetail = orderDetailEntity.get();
        }
        OrderDetailDTO orderDetailDTO = OrderDetailDTO.toOrderDetailDTO(orderDetail);
        return orderDetailDTO;
    }

}
