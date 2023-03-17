package com.project.kall.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.kall.entity.*;
import lombok.*;

import java.util.List;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailDTO {

    private Integer orderDnb;
    private OrderDTO orderDTO;
    private ProductDTO productDTO;
    private String option_sheet;
    private String option_shape;
    private String option_cream;
    private String option_lettering;
    private String option_size;
    private String option_image;
    private Integer amount;
    private Integer price;
    private String status;

    public OrderDTO getOrderDTO() {
        return orderDTO;
    }

    public void setOrderDTO(OrderDTO order) {
        this.orderDTO = order;
    }

    public ProductDTO getProductDTO() {
        return productDTO;
    }

    public void setProductDTO(ProductDTO product) {
        this.productDTO = product;
    }

    public static OrderDetailDTO toOrderDetailDTO(OrderDetailEntity orderDetailEntity) {
        OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
        if(orderDetailEntity != null) {

            orderDetailDTO.setOrderDnb(orderDetailEntity.getOrderDnb());
            orderDetailDTO.setOrderDTO(OrderDTO.toOrderDTO(orderDetailEntity.getOrderEntity()));
            orderDetailDTO.setProductDTO(ProductDTO.toProductDTO(orderDetailEntity.getProductEntity()));
            orderDetailDTO.setOption_sheet(orderDetailEntity.getOption_sheet());
            orderDetailDTO.setOption_shape(orderDetailEntity.getOption_shape());
            orderDetailDTO.setOption_cream(orderDetailEntity.getOption_cream());
            orderDetailDTO.setOption_lettering(orderDetailEntity.getOption_lettering());
            orderDetailDTO.setOption_size(orderDetailEntity.getOption_size());
            orderDetailDTO.setOption_image(orderDetailEntity.getOption_image());
            orderDetailDTO.setAmount(orderDetailEntity.getAmount());
            orderDetailDTO.setPrice(orderDetailEntity.getPrice());
            orderDetailDTO.setStatus(orderDetailEntity.getStatus());
        }
        return orderDetailDTO;
    }
}
