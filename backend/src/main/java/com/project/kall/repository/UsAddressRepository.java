package com.project.kall.repository;

import com.project.kall.entity.UsAddressEntity;
import com.project.kall.entity.UsQEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// 도진
public interface UsAddressRepository extends JpaRepository<UsAddressEntity, Integer> {
    List<UsAddressEntity> findAllByUserEntityUserIdAndUsAddressIdNot(String userId, Integer addressId);
}
