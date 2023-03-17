package com.project.kall.repository;

import com.project.kall.entity.OrderDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

// 수진

public interface OrderDetailRepository extends JpaRepository<OrderDetailEntity, Integer> {

    @Query(value = "select rf, od from RefundEntity rf join rf.orderDetailEntity od where rf.refundId = (:id)")
    List<Object[]> rfDetailList(@Param("id") Integer id);

    @Query(value="select od from OrderDetailEntity od join od.orderEntity ore where ore.id = (:id)")
    List<OrderDetailEntity> findOrderId (@Param("id") Integer id);
    // Query랑 함수명에서 jpa 랑 연관된 예약어 표헌 (findBy, or 이런거)  사용 XXXXX 사용하면 에러남

    @Query(value = "select od from RefundEntity rf join rf.orderDetailEntity od where od.orderDnb = (:id)")
    Optional<OrderDetailEntity> findByOrderDnb(@Param("id") Integer orderDnb);

}
