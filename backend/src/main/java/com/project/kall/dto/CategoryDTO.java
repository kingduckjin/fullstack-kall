package com.project.kall.dto;

import com.project.kall.entity.CategoryEntity;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
    private String categoryId;
    private String categoryName;

    public static CategoryDTO toCategoryDTO(CategoryEntity categoryEntity) {
        CategoryDTO categoryDTO = new CategoryDTO();

        categoryDTO.setCategoryId(categoryEntity.getCategoryId());
        categoryDTO.setCategoryName(categoryEntity.getCategoryName());
        return categoryDTO;
    }
}
