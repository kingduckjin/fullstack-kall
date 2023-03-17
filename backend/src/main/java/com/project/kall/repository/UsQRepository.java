package com.project.kall.repository;

import com.project.kall.entity.UsQEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UsQRepository extends JpaRepository<UsQEntity, Integer> {

    @Query(value="select usq from UsQEntity usq join usq.userEntity us where us.userId=(:userId)")
    List<UsQEntity> userId(@Param("userId") String userId);
    void deleteByUserEntityUserId(String userId);

    List<UsQEntity> findAllByOrderByUsQIdDesc();

}