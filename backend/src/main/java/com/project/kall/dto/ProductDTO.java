package com.project.kall.dto;

import com.project.kall.entity.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private Integer productId;
    private CategoryDTO categoryDTO;
    private String name;
    private Integer price;
    private Integer amount;
    private String info;
    private LocalDateTime date;
    private Float star;
    private String tag;
    private String image;
    private Integer cnt;

    private MultipartFile file;

    //private List<ProductImgDTO> productImgDTOList = new ArrayList<>();


    public static ProductDTO toProductDTO(ProductEntity productEntity) {
        if(productEntity == null) return null;
        ProductDTO productDTO = new ProductDTO();

        productDTO.setProductId(productEntity.getProductId());
        productDTO.setCategoryDTO(CategoryDTO.toCategoryDTO(productEntity.getCategoryEntity()));
        productDTO.setName(productEntity.getName());
        productDTO.setPrice(productEntity.getPrice());
        productDTO.setAmount(productEntity.getAmount());
        productDTO.setInfo(productEntity.getInfo());
        productDTO.setDate(productEntity.getDate());
        productDTO.setStar(productEntity.getStar());
        productDTO.setTag(productEntity.getTag());
        productDTO.setImage(productEntity.getImage());
        productDTO.setCnt(productEntity.getCnt());

        return productDTO;
    }


}
