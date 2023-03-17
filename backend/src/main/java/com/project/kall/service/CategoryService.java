package com.project.kall.service;

import com.project.kall.dto.CategoryDTO;
import com.project.kall.dto.ProductDTO;
import com.project.kall.entity.CategoryEntity;
import com.project.kall.entity.ProductEntity;
import com.project.kall.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public CategoryDTO getCategoryById(String categoryId) {
        CategoryEntity categoryEntity = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        return new CategoryDTO(categoryEntity.getCategoryId(), categoryEntity.getCategoryName());
    }
}


