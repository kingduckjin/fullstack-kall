package com.project.kall.entity;

import com.project.kall.dto.ProductImgDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@Transactional
@Table(name = "product_img")
public class ProductImgEntity {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer productimgid;

    @ManyToOne
    @JoinColumn(name = "productId")
    private ProductEntity productEntity;

    @Column(nullable = false, length = 100)
    private String oriImgName;
    @Column(nullable = false, length = 100)
    private String imgUrl;

    @Column(nullable = false, length = 100)
    private String imgName;

    public static ProductImgEntity toSetProductImgEntity(ProductImgDTO productImgDTO) {
        ProductImgEntity productImgEntity = new ProductImgEntity();
        productImgEntity.setProductimgid(productImgDTO.getProductimgid());
        productImgEntity.setProductEntity(ProductEntity.toSetProductEntity(productImgDTO.getProductDTO()));
        productImgEntity.setOriImgName(productImgDTO.getOriImgName());
        productImgEntity.setImgUrl(productImgDTO.getImgUrl());
        productImgEntity.setImgName(productImgDTO.getImgName());
        return productImgEntity;
    }
}
