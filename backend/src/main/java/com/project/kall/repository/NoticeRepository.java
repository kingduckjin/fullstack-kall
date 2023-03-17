package com.project.kall.repository;

import com.project.kall.entity.NoticeEntity;
import com.project.kall.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NoticeRepository extends JpaRepository<NoticeEntity, Integer> {



    List<NoticeEntity> findAllByOrderByNoticeIdDesc();
}
