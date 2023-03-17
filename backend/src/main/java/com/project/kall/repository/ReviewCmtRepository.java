package com.project.kall.repository;

import com.project.kall.entity.ReviewCmtEntity;
import com.project.kall.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;

//수진

public interface ReviewCmtRepository extends JpaRepository<ReviewCmtEntity, Integer> {
    @Query(value = "select rc from ReviewCmtEntity rc join rc.reviewEntity rv where rv.reviewId = (:reviewId) order by rc.id desc")
    List<ReviewCmtEntity> ReviewId(@Param("reviewId") Integer reviewId);
}
