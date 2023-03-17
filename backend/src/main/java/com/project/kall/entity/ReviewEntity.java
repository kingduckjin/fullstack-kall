package com.project.kall.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.kall.dto.ReviewDTO;
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
@Table(name = "review")
public class ReviewEntity {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer reviewId;
    @OneToMany(mappedBy = "reviewEntity", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ReviewCmtEntity> reviewCmtEntities = new ArrayList<>();

    @JoinColumn(name = "userId")
    @ManyToOne
    private UserEntity userEntity;
    @ManyToOne
    @JoinColumn(name = "productId")
    private ProductEntity productEntity;
    @ManyToOne
    @JoinColumn(name = "order_dnb")
    private OrderDetailEntity orderDetailEntity;
    @Column(nullable = false, length = 50)
    private String title;
    @Column(nullable = false, length = 500)
    private String content;
    @Column(nullable = false, length = 2)
    private Float star;
    @CreationTimestamp
    private LocalDateTime date = LocalDateTime.now();
    @Column(length = 100)
    private String image_1;
    @Column(length = 100)
    private String image_2;

    public static ReviewEntity toReviewEntity(ReviewDTO reviewDTO) {
        ReviewEntity reviewEntity = new ReviewEntity();
        if (reviewDTO != null) {
            reviewEntity.setReviewId(reviewDTO.getReviewId());
            reviewEntity.setTitle(reviewDTO.getTitle());
            reviewEntity.setContent(reviewDTO.getContent());
            reviewEntity.setStar(reviewDTO.getStar());
            reviewEntity.setDate(reviewDTO.getDate());
            reviewEntity.setUserEntity(UserEntity.toUserEntity(reviewDTO.getUserDTO()));
            reviewEntity.setProductEntity(ProductEntity.toSetProductEntity(reviewDTO.getProductDTO()));
            reviewEntity.setOrderDetailEntity(OrderDetailEntity.toSetOrderDetailEntity(reviewDTO.getOrderDetailDTO()));
        }
        return reviewEntity;
    }

}
