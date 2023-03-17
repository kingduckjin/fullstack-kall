package com.project.kall.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project.kall.dto.OrderDetailDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@Table(name = "us_odetail")
public class OrderDetailEntity {
    @Id @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderDnb;

    @OneToMany(mappedBy = "orderDetailEntity", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<RefundEntity> refundEntities = new ArrayList<>();

    @OneToMany(mappedBy = "orderDetailEntity", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ReviewEntity> reviewEntities = new ArrayList<>();

    @JoinColumn(name = "orderId")
    @ManyToOne
    private OrderEntity orderEntity;

    @ManyToOne
    @JoinColumn(name = "productId")
    private ProductEntity productEntity;

    @Column(length = 20)
    private String option_sheet;
    @Column(length = 20)
    private String option_shape;
    @Column(length = 20)
    private String option_cream;
    @Column(length = 30)
    private String option_lettering;
    @Column(length = 20)
    private String option_size;
    @Column(length = 100)
    private String option_image;

    @Column(nullable = false, length = 3)
    private Integer amount;
    @Column(nullable = false, length = 8)
    private Integer price;

    @Column(length = 20)
    private String status = "결제 완료";

    @Column(length = 50)
    private String tmp_2;


    public static OrderDetailEntity toSetOrderDetailEntity(OrderDetailDTO orderdetailDTO) {
        OrderDetailEntity orderDetailEntity = new OrderDetailEntity();
        if(orderdetailDTO != null) {


            orderDetailEntity.setOrderDnb(orderdetailDTO.getOrderDnb());
            orderDetailEntity.setOrderEntity(OrderEntity.toSetOrderEntity(orderdetailDTO.getOrderDTO()));
            orderDetailEntity.setProductEntity(ProductEntity.toSetProductEntity(orderdetailDTO.getProductDTO()));
            orderDetailEntity.setOption_sheet(orderdetailDTO.getOption_sheet());
            orderDetailEntity.setOption_shape(orderdetailDTO.getOption_shape());
            orderDetailEntity.setOption_cream(orderdetailDTO.getOption_cream());
            orderDetailEntity.setOption_lettering(orderdetailDTO.getOption_lettering());
            orderDetailEntity.setOption_size(orderdetailDTO.getOption_size());
            orderDetailEntity.setOption_image(orderdetailDTO.getOption_image());
            orderDetailEntity.setAmount(orderdetailDTO.getAmount());
            orderDetailEntity.setPrice(orderdetailDTO.getPrice());
            orderDetailEntity.setStatus(orderdetailDTO.getStatus());
        }
        return orderDetailEntity;
    }
}
