package com.project.kall.dto;

import com.project.kall.entity.OrderDetailEntity;
import com.project.kall.entity.ProductEntity;
import com.project.kall.entity.ReviewEntity;
import com.project.kall.entity.UserEntity;
import lombok.*;
import org.apache.catalina.User;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {

    private Integer reviewId;
    private UserDTO userDTO;
    private ProductDTO productDTO;
    private OrderDetailDTO orderDetailDTO;
    private String title;
    private String content;
    private Float star;
    private LocalDateTime date;
    private String Image_1;
    private String Image_2;

    public static ReviewDTO toReviewDTO(ReviewEntity reviewEntity) {
        ReviewDTO reviewDTO = new ReviewDTO();

        if (reviewEntity != null) {
            reviewDTO.setReviewId(reviewEntity.getReviewId());
            reviewDTO.setUserDTO(UserDTO.toUserDTO(reviewEntity.getUserEntity()));
            reviewDTO.setProductDTO(ProductDTO.toProductDTO(reviewEntity.getProductEntity()));
            reviewDTO.setOrderDetailDTO(OrderDetailDTO.toOrderDetailDTO(reviewEntity.getOrderDetailEntity()));
            reviewDTO.setTitle(reviewEntity.getTitle());
            reviewDTO.setContent(reviewEntity.getContent());
            reviewDTO.setStar(reviewEntity.getStar());
            reviewDTO.setDate(reviewEntity.getDate());
        }

        return reviewDTO;
    }
}
