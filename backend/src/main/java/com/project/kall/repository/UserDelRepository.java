package com.project.kall.repository;

import com.project.kall.dto.UserDelDTO;
import com.project.kall.entity.UserDelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDelRepository extends JpaRepository<UserDelEntity, String> {

    void save(UserDelDTO userDelDTO);

}
