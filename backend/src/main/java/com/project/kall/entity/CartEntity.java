package com.project.kall.entity;

import com.project.kall.dto.CartDTO;
import com.project.kall.dto.OrderDetailDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity
@Getter@Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@Table(name = "cart")
public class CartEntity {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer cartId;
    @JoinColumn(name = "productId")
    @ManyToOne
    private ProductEntity productEntity;
    @JoinColumn(name = "userId")
    @ManyToOne
    private UserEntity userEntity;

    @Column(length = 20)
    private String option_sheet;
    @Column(length = 20)
    private String option_shape;
    @Column(length = 20)
    private String option_cream;
    @Column(length = 30)
    private String option_lettering;
    @Column(length = 20)
    private String option_size;
    @Column(length = 100)
    private String option_image;
    @Column(nullable = false, length=3)
    private Integer amount;
    @Column(nullable = false, length = 8)
    private Integer price;
    @Column(length = 50)
    private String tmp_1;
    @Column(length = 50)
    private String tmp_2;

    // 수진 toCartEntity 필
    public static CartEntity toCartEntity(CartDTO cartDTO) {
        if(cartDTO == null) return null;

        CartEntity cartEntity = new CartEntity();

        cartEntity.setCartId(cartDTO.getCartId());
        cartEntity.setProductEntity(ProductEntity.toSetProductEntity(cartDTO.getProductDTO()));
        cartEntity.setUserEntity(UserEntity.toUserEntity(cartDTO.getUserDTO()));
        cartEntity.setOption_sheet(cartDTO.getOption_sheet());
        cartEntity.setOption_shape(cartDTO.getOption_shape());
        cartEntity.setOption_cream(cartDTO.getOption_cream());
        cartEntity.setOption_lettering(cartDTO.getOption_lettering());
        cartEntity.setOption_size(cartDTO.getOption_size());
        cartEntity.setOption_image(cartDTO.getOption_image());
        cartEntity.setAmount(cartDTO.getAmount());
        cartEntity.setPrice(cartDTO.getPrice());

        return cartEntity;
    }
}
