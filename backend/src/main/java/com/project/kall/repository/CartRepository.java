package com.project.kall.repository;
import com.project.kall.entity.CartEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

// 수진
public interface CartRepository extends JpaRepository<CartEntity, Integer> {

//    -----------------------------------userId 별 카트 목록 --------------------------------------------------------

    List<CartEntity> findByUserEntityUserId(String userId);
}
