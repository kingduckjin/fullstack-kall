package com.project.kall.dto;

import com.project.kall.entity.OrderDetailEntity;
import com.project.kall.entity.RefundEntity;
import com.project.kall.entity.UserEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RefundDTO {

    private Integer refundId;
    private OrderDetailDTO orderDetailDTO;
    private UserDTO userDTO;
    private String title;
    private String content;
    private String image_1;
    private String image_2;
    private String status;
    private LocalDateTime date;


    public static RefundDTO toRefundDTO(RefundEntity refundEntity) {
        if(refundEntity == null) return null;
        RefundDTO refundDTO = new RefundDTO();

        refundDTO.setRefundId(refundEntity.getRefundId());
        refundDTO.setOrderDetailDTO(OrderDetailDTO.toOrderDetailDTO(refundEntity.getOrderDetailEntity()));
        refundDTO.setUserDTO(UserDTO.toUserDTO(refundEntity.getUserEntity()));
        refundDTO.setTitle(refundEntity.getTitle());
        refundDTO.setContent(refundEntity.getContent());
        refundDTO.setImage_1(refundEntity.getImage_1());
        refundDTO.setImage_2(refundEntity.getImage_2());
        refundDTO.setStatus(refundEntity.getStatus());
        refundDTO.setDate(refundEntity.getDate());

        return refundDTO;
    }
}
