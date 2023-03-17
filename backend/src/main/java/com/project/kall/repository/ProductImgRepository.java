package com.project.kall.repository;

import com.project.kall.entity.ProductImgEntity;
import com.project.kall.entity.ProductQEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImgRepository extends JpaRepository<ProductImgEntity, Integer> {

    List<ProductImgEntity> findByProductEntityProductId(Integer productId);

}
