package com.project.kall.dto;

import com.project.kall.entity.ProductEntity;
import com.project.kall.entity.ProductQEntity;
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
public class ProductQDTO {

    private Integer productQId;
    private UserDTO userDTO;
    private ProductDTO productDTO;
    private String title;
    private String content;
    private String comment;
    private LocalDateTime date1;
    private LocalDateTime date2;

    public static ProductQDTO toProductQDTO(ProductQEntity productQEntity) {
        if (productQEntity == null) {
            return null;
        }
        ProductQDTO productQDTO = new ProductQDTO();
        productQDTO.setProductQId(productQEntity.getProductQId());
        productQDTO.setUserDTO(UserDTO.toUserDTO(productQEntity.getUserEntity()));
        productQDTO.setProductDTO(ProductDTO.toProductDTO(productQEntity.getProductEntity()));
        productQDTO.setTitle(productQEntity.getTitle());
        productQDTO.setContent(productQEntity.getContent());
        productQDTO.setComment(productQEntity.getComment());
        productQDTO.setDate1(productQEntity.getDate1());
        productQDTO.setDate2(productQEntity.getDate2());
        return productQDTO;
    }
}
