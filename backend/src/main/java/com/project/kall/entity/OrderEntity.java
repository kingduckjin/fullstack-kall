package com.project.kall.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.kall.dto.OrderDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@Table(name = "us_order")
public class OrderEntity {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer orderId;
    @OneToMany(mappedBy = "orderEntity", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<OrderDetailEntity> orderDetailEntities = new ArrayList<>();

    @JoinColumn(name = "userId")
    @ManyToOne
    private UserEntity userEntity;

    @Column(nullable = false, length = 10)
    private String name;
    @Column(nullable = false, length = 10)
    private String zip_code;
    @Column(nullable = false, length = 100)
    private String address_1;
    @Column(length = 100)
    private String address_2;
    @Column(nullable = false, length = 15)
    private String phone_nb;
    @Column(length = 100)
    private String request;
    @CreationTimestamp
    private LocalDateTime date= LocalDateTime.now();
    @Column(length = 8)
    private Integer mileage=0;
    @Column(nullable = false, length = 8)
    private Integer price;
    @Column(nullable = false, length = 40)
    private String payment;
    @Column(length = 40)
    private String order_status = "결제 완료";
    @Column(length = 50)
    private String tmp_1;
    @Column(length = 50)
    private String tmp_2;

    public static OrderEntity toSetOrderEntity(OrderDTO orderDTO) {
        OrderEntity orderEntity = new OrderEntity();
        if (orderDTO != null) {
            orderEntity.setOrderId(orderDTO.getOrderId());
            orderEntity.setUserEntity(UserEntity.toUserEntity(orderDTO.getUserDTO()));
            orderEntity.setName(orderDTO.getName());
            orderEntity.setZip_code(orderDTO.getZip_code());
            orderEntity.setAddress_1(orderDTO.getAddress_1());
            orderEntity.setAddress_2(orderDTO.getAddress_2());
            orderEntity.setPhone_nb(orderDTO.getPhone_nb());
            orderEntity.setRequest(orderDTO.getRequest());
            orderEntity.setDate(orderDTO.getDate());
            orderEntity.setMileage(orderDTO.getMileage());
            orderEntity.setPrice(orderDTO.getPrice());
            orderEntity.setPayment(orderDTO.getPayment());
            orderEntity.setOrder_status(orderDTO.getOrder_status());
        }
        return orderEntity;
    }

}