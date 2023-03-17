package com.project.kall.dto;

import com.project.kall.entity.ProductEntity;
import com.project.kall.entity.ProductImgEntity;
import com.project.kall.entity.UserEntity;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductImgDTO {

    private Integer productimgid;
    private ProductDTO productDTO;
    private String oriImgName;
    private String imgUrl;

    private String imgName;
    
    private MultipartFile file;

    public static ProductImgDTO toProductImgDTO(ProductImgEntity productImgEntity) {
        ProductImgDTO productImgDTO = new ProductImgDTO();
        productImgDTO.setProductimgid(productImgEntity.getProductimgid());
        productImgDTO.setProductDTO(ProductDTO.toProductDTO(productImgEntity.getProductEntity()));
        productImgDTO.setOriImgName(productImgEntity.getOriImgName());
        productImgDTO.setImgUrl(productImgEntity.getImgUrl());
        productImgDTO.setImgName(productImgEntity.getImgName());
        return productImgDTO;
    }


}
