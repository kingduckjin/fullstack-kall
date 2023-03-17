package com.project.kall.repository;

import com.project.kall.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

// 수진
public interface OrderRepository extends JpaRepository<OrderEntity, Integer> {

//    ---------------------------------유저 별 주문 리스트----------------------------------------------------------
    @Query(value = "select oe from OrderEntity oe join oe.userEntity ue where ue.id=(:id)")
    List<OrderEntity> findOrderId(@Param("id") String id);

    List<OrderEntity> findAllByOrderByOrderIdDesc();
}
