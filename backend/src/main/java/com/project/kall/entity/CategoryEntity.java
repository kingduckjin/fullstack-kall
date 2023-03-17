package com.project.kall.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.kall.dto.CategoryDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter@Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@Table(name = "category")
public class CategoryEntity {
    @Id @Column(name="id", length = 20)
    private String categoryId;
    @OneToMany(mappedBy = "categoryEntity", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<NoticeEntity> noticeEntities = new ArrayList<>();
    @OneToMany(mappedBy = "categoryEntity", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ProductEntity> productEntities = new ArrayList<>();
    @OneToMany(mappedBy = "categoryEntity", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<UsQEntity> usQEntities = new ArrayList<>();

    @Column(name = "category_name", length = 50, nullable = false)
    private String categoryName;

    public static CategoryEntity toCategoryEntity(CategoryDTO categoryDTO) {
        CategoryEntity categoryEntity = new CategoryEntity();

        if(categoryDTO != null) {
            categoryEntity.setCategoryId(categoryDTO.getCategoryId());
            categoryEntity.setCategoryName(categoryDTO.getCategoryName());
        }
        return categoryEntity;
    }
}
