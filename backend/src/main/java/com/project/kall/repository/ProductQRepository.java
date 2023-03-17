package com.project.kall.repository;

import com.project.kall.entity.ProductQEntity;
import com.project.kall.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductQRepository extends JpaRepository<ProductQEntity, Integer> {
    List<ProductQEntity> findAllByOrderByProductQIdDesc();
}