package com.project.kall.entity;

import com.project.kall.dto.CategoryDTO;
import com.project.kall.dto.ProductQDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@Table(name = "product_q")
public class ProductQEntity {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer productQId;

    @JoinColumn(name = "userId")
    @ManyToOne
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "productId")
    private ProductEntity productEntity;
    @Column(nullable = false, length = 50)
    private String title;
    @Column(nullable = false, length = 500)
    private String content;
    @Column(length = 500)
    private String comment;
    @CreationTimestamp
    private LocalDateTime date1 = LocalDateTime.now();
    @CreationTimestamp
    private LocalDateTime date2 = LocalDateTime.now();

    public static ProductQEntity toProductQEntity(ProductQDTO productQDTO) {
        ProductQEntity productQEntity = new ProductQEntity();
        productQEntity.setProductQId(productQDTO.getProductQId());
        productQEntity.setUserEntity(UserEntity.toUserEntity(productQDTO.getUserDTO()));
        productQEntity.setProductEntity(ProductEntity.toSetProductEntity(productQDTO.getProductDTO()));
        productQEntity.setTitle(productQDTO.getTitle());
        productQEntity.setContent(productQDTO.getContent());
        productQEntity.setComment(productQDTO.getComment());
        productQEntity.setDate1(productQDTO.getDate1());
        productQEntity.setDate2(productQDTO.getDate2());
        return productQEntity;
    }
}
