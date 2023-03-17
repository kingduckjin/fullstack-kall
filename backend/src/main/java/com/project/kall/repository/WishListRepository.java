package com.project.kall.repository;

import com.project.kall.entity.UsQEntity;
import com.project.kall.entity.WishListEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WishListRepository  extends JpaRepository<WishListEntity, Integer> {
    @Query(value = "select wl from WishListEntity wl join wl.userEntity ue where ue.id=(:id)")
    List<WishListEntity> findUserId(@Param("id") String id);
}
