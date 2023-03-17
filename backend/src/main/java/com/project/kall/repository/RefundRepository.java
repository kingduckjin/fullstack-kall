package com.project.kall.repository;

import com.project.kall.entity.RefundEntity;
import com.project.kall.entity.ReviewCmtEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RefundRepository extends JpaRepository<RefundEntity, Integer> {

    List<RefundEntity> findAllByOrderByRefundIdDesc();
}
