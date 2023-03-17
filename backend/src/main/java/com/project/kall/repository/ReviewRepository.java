package com.project.kall.repository;

import com.project.kall.entity.ReviewCmtEntity;
import com.project.kall.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Integer> {

    List<ReviewEntity> findAllByOrderByReviewIdDesc();
}