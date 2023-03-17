package com.project.kall.repository;

import com.project.kall.entity.ProductEntity;
import com.project.kall.entity.ProductImgEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {
    List<ProductEntity> findTop6ByOrderByProductIdDesc();

//    @Query("select pd from ProductEntity pd join pd.categoryEntity ct where ct.categoryId = (:categoryId) ")
//    List<ProductEntity> findCategoryEntityCategoryId(@Param("categoryId") String categoryId);
    // name이나 tag검색 가능하게
    List<ProductEntity> findByNameContainingIgnoreCaseOrTagContainingIgnoreCase(String nameKeyword, String tagKeyword);

    @Query(value = "select pd from ProductEntity pd join pd.categoryEntity ce where ce.id=(:id)")
    List<ProductEntity> findCategoryName(@Param("id") String id);


}
