package com.project.kall.entity;


import com.project.kall.dto.ReviewCmtDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter@Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@Table(name = "review_cmt")
public class ReviewCmtEntity {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer reviewCmtId;

    @JoinColumn(name = "reviewNb")
    @ManyToOne
    private ReviewEntity reviewEntity;

    @JoinColumn(name = "userId")
    @ManyToOne
    private UserEntity userEntity;

    @Column(nullable = false, length = 50)
    private String title;
    @Column(nullable = false, length = 500)
    private String content;
    @CreationTimestamp
    private LocalDateTime date = LocalDateTime.now();

    public static ReviewCmtEntity toReviewCmtEntity(ReviewCmtDTO reviewCmtDTO) {
        ReviewCmtEntity reviewCmtEntity = new ReviewCmtEntity();
        reviewCmtEntity.setReviewCmtId(reviewCmtDTO.getReviewCmtId());
        reviewCmtEntity.setReviewEntity(ReviewEntity.toReviewEntity(reviewCmtDTO.getReviewDTO()));
        reviewCmtEntity.setUserEntity(UserEntity.toUserEntity(reviewCmtDTO.getUserDTO()));
        reviewCmtEntity.setTitle(reviewCmtDTO.getTitle());
        reviewCmtEntity.setContent(reviewCmtDTO.getContent());
        reviewCmtEntity.setDate(reviewCmtDTO.getDate());
        return reviewCmtEntity;
    }
}
