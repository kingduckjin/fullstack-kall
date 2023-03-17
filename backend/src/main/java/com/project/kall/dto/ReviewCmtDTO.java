package com.project.kall.dto;

import com.project.kall.entity.ReviewCmtEntity;
import com.project.kall.entity.ReviewEntity;
import com.project.kall.entity.UserEntity;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ReviewCmtDTO {

    private Integer reviewCmtId;
    private UserDTO userDTO;
    private ReviewDTO reviewDTO;
    private String title;
    private String content;
    private LocalDateTime date;

    public static ReviewCmtDTO toReviewCmtDTO(ReviewCmtEntity reviewCmtEntity) {
        if(reviewCmtEntity == null) return null;
        ReviewCmtDTO reviewCmtDTO = new ReviewCmtDTO();

        reviewCmtDTO.setReviewCmtId(reviewCmtEntity.getReviewCmtId());
        reviewCmtDTO.setUserDTO(UserDTO.toUserDTO(reviewCmtEntity.getUserEntity()));
        reviewCmtDTO.setReviewDTO(ReviewDTO.toReviewDTO(reviewCmtEntity.getReviewEntity()));
        reviewCmtDTO.setTitle(reviewCmtEntity.getTitle());
        reviewCmtDTO.setContent(reviewCmtEntity.getContent());
        reviewCmtDTO.setDate(reviewCmtEntity.getDate());

        return reviewCmtDTO;
    }
}
