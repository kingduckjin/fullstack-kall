package com.project.kall.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.kall.dto.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter@Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@Transactional
@Table(name = "product")
public class ProductEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer productId;
    @OneToMany(mappedBy = "productEntity", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ProductImgEntity> productImgEntities = new ArrayList<>();
    @OneToMany(mappedBy = "productEntity", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<OrderDetailEntity> orderDetailEntities = new ArrayList<>();
    @OneToMany(mappedBy = "productEntity", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ReviewEntity> reviewEntities = new ArrayList<>();
    @OneToMany(mappedBy = "productEntity", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<CartEntity> cartEntities = new ArrayList<>();
    @OneToMany(mappedBy = "productEntity", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<WishListEntity> wishListEntities = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private CategoryEntity categoryEntity;

    @Column(nullable = false, length = 50)
    private String name;
    @Column(nullable = false, length = 8)
    private Integer price;
    //@Column(nullable = false, length = 3)
    @Column( length = 3)
    private Integer amount;
    @Column(nullable = false, length = 1000)
    private String info;

    @CreationTimestamp
    private LocalDateTime date = LocalDateTime.now();

    @ColumnDefault("0")
    @Column(length = 2)
    private Float star;
    @Column(length = 100)
    private String tag;
    @Column(nullable = false, length = 100)
    private String image;
    @ColumnDefault("0")
    @Column(length = 5)
    private Integer cnt;

    @Column(length = 50)
    private String tmp_1;
    @Column(length = 50)
    private String tmp_2;

    public static ProductEntity toSetProductEntity(ProductDTO productDTO) {
        ProductEntity productEntity = new ProductEntity();

        productEntity.setProductId(productDTO.getProductId());
        productEntity.setCategoryEntity(CategoryEntity.toCategoryEntity(productDTO.getCategoryDTO()));
        productEntity.setName(productDTO.getName());
        productEntity.setPrice(productDTO.getPrice());
        productEntity.setAmount(productDTO.getAmount());
        productEntity.setInfo(productDTO.getInfo());
        productEntity.setDate(productDTO.getDate());
        productEntity.setStar(productDTO.getStar());
        productEntity.setTag(productDTO.getTag());
        productEntity.setImage(productDTO.getImage());
        productEntity.setCnt(productDTO.getCnt());

        return productEntity;
    }
}
