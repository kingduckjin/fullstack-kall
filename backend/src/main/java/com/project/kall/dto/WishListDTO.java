package com.project.kall.dto;

import com.project.kall.entity.ProductEntity;
import com.project.kall.entity.UserEntity;
import com.project.kall.entity.WishListEntity;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class WishListDTO {

    private Integer wishListId;
    private ProductDTO productDTO;
    private UserDTO userDTO;
    private LocalDateTime date;

    public static WishListDTO toWishListDTO(WishListEntity wishListEntity) {
        WishListDTO wishListDTO = new WishListDTO();

        wishListDTO.setWishListId(wishListEntity.getWishListId());
        wishListDTO.setProductDTO(ProductDTO.toProductDTO(wishListEntity.getProductEntity()));
        wishListDTO.setUserDTO(UserDTO.toUserDTO(wishListEntity.getUserEntity()));
        wishListDTO.setDate(wishListEntity.getDate());

        return wishListDTO;
    }
}
