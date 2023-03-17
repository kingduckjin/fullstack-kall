package com.project.kall.service;


import com.project.kall.dto.OrderDTO;
import com.project.kall.entity.OrderEntity;
import com.project.kall.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserOrderService {
    private final OrderRepository orderRepository;
    //━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ 도진 ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    public List<OrderDTO> userFindAll(String userId) {
        List<OrderEntity> orderEntities = orderRepository.findAll();
        List<OrderDTO> orderDTOList = new ArrayList<>();
        for (OrderEntity order : orderEntities) {
            if (userId.equals(order.getUserEntity().getUserId())) {
                orderDTOList.add(OrderDTO.toOrderDTO(order));
            }
        }
        return orderDTOList;
    }

    public OrderDTO userFindById(Integer orderId) {
        Optional<OrderEntity> userEntity = orderRepository.findById(orderId);
        if (userEntity.isPresent()) {
            OrderEntity order = userEntity.get();
            OrderDTO orderDTO = OrderDTO.toOrderDTO(order);
            return orderDTO;
        } else return null;
    }





}
