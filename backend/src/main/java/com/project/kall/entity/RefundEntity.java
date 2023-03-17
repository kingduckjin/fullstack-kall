package com.project.kall.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.kall.dto.RefundDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@Table(name = "refund")
public class RefundEntity {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer refundId;
    @JoinColumn(name = "order_dnb")
    @ManyToOne
    private OrderDetailEntity orderDetailEntity;
    @ManyToOne
    @JoinColumn(name = "userId")
    private UserEntity userEntity;
    @Column(nullable = false, length = 50)
    private String title;
    @Column(nullable = false, length = 500)
    private String content;
    @Column(length = 100)
    private String image_1;
    @Column(length = 100)
    private String image_2;
    @Column(length = 30)
    private String status = "반품 요청";
    @CreationTimestamp
    private LocalDateTime date = LocalDateTime.now();

    @Column(length = 50)
    private String tmp_2;

    public static RefundEntity toSetRefundEntity(RefundDTO refundDTO) {
        RefundEntity refundEntity = new RefundEntity();

        refundEntity.setRefundId(refundDTO.getRefundId());
        refundEntity.setOrderDetailEntity(OrderDetailEntity.toSetOrderDetailEntity(refundDTO.getOrderDetailDTO()));
        refundEntity.setUserEntity(UserEntity.toUserEntity(refundDTO.getUserDTO()));
        refundEntity.setTitle(refundDTO.getTitle());
        refundEntity.setContent(refundDTO.getContent());
        refundEntity.setImage_1(refundDTO.getImage_1());
        refundEntity.setImage_2(refundDTO.getImage_2());
        refundEntity.setStatus(refundDTO.getStatus());
        refundEntity.setDate(refundDTO.getDate());
        refundEntity.setTmp_2(refundDTO.getImage_2());
        return refundEntity;
    }
}
