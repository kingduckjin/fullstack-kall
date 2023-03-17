package com.project.kall.entity;

import com.project.kall.dto.UserDTO;
import com.project.kall.dto.WishListDTO;
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
@Table(name = "wish_list")
public class WishListEntity {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer wishListId;

    @JoinColumn(name = "productId")
    @ManyToOne
    private ProductEntity productEntity;

    @JoinColumn(name = "userId")
    @ManyToOne
    private UserEntity userEntity;

    @CreationTimestamp
    private LocalDateTime date= LocalDateTime.now();

    public static WishListEntity toWishListEntity(WishListDTO wishListDTO) {
        if (wishListDTO == null) return null;
        WishListEntity wishListEntity = new WishListEntity();

        wishListEntity.setWishListId(wishListDTO.getWishListId());
        wishListEntity.setProductEntity(ProductEntity.toSetProductEntity(wishListDTO.getProductDTO()));
        wishListEntity.setUserEntity(UserEntity.toUserEntity(wishListDTO.getUserDTO()));
        wishListEntity.setDate(wishListDTO.getDate());
        return wishListEntity;
    }
}
