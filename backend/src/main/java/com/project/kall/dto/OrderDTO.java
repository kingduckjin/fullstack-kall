package com.project.kall.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.kall.entity.OrderDetailEntity;
import com.project.kall.entity.OrderEntity;
import com.project.kall.entity.UserEntity;
import lombok.*;
import org.aspectj.weaver.ast.Or;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private Integer orderId;
    private UserDTO userDTO;
    private String name;
    private String zip_code;
    private String address_1;
    private String address_2;
    private String phone_nb;
    private String request;
    private LocalDateTime date;
    private Integer mileage;
    private Integer price;
    private String payment;
    private String order_status;


    public static OrderDTO toOrderDTO(OrderEntity orderEntity) {
        if(orderEntity == null) return null;
        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setOrderId(orderEntity.getOrderId());
        orderDTO.setUserDTO(UserDTO.toUserDTO(orderEntity.getUserEntity()));
        orderDTO.setName(orderEntity.getName());
        orderDTO.setZip_code(orderEntity.getZip_code());
        orderDTO.setAddress_1(orderEntity.getAddress_1());
        orderDTO.setAddress_2(orderEntity.getAddress_2());
        orderDTO.setPhone_nb(orderEntity.getPhone_nb());
        orderDTO.setRequest(orderEntity.getRequest());
        orderDTO.setDate(orderEntity.getDate());
        orderDTO.setMileage(orderEntity.getMileage());
        orderDTO.setPrice(orderEntity.getPrice());
        orderDTO.setPayment(orderEntity.getPayment());
        orderDTO.setOrder_status(orderEntity.getOrder_status());

        return orderDTO;
    }
}
