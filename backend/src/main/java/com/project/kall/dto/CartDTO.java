package com.project.kall.dto;

import com.project.kall.entity.CartEntity;
import com.project.kall.entity.CategoryEntity;
import com.project.kall.entity.ProductEntity;
import com.project.kall.entity.UserEntity;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {
    private Integer cartId;
    private ProductDTO productDTO;
    private UserDTO userDTO;
    private String option_sheet;
    private String option_shape;
    private String option_cream;
    private String option_lettering;
    private String option_size;
    private String option_image;
    private Integer amount;
    private Integer price;

    public static CartDTO toCartDTO(CartEntity cartEntity) {
        if (cartEntity == null) return null;
        CartDTO cartDTO = new CartDTO();

        cartDTO.setCartId(cartEntity.getCartId());
        cartDTO.setProductDTO(ProductDTO.toProductDTO(cartEntity.getProductEntity()));
        cartDTO.setUserDTO(UserDTO.toUserDTO(cartEntity.getUserEntity()));
        cartDTO.setOption_sheet(cartEntity.getOption_sheet());
        cartDTO.setOption_shape(cartEntity.getOption_shape());
        cartDTO.setOption_cream(cartEntity.getOption_cream());
        cartDTO.setOption_lettering(cartEntity.getOption_lettering());
        cartDTO.setOption_size(cartEntity.getOption_size());
        cartDTO.setOption_image(cartEntity.getOption_image());
        cartDTO.setAmount(cartEntity.getAmount());
        cartDTO.setPrice(cartEntity.getPrice());

        return cartDTO;
    }
}
