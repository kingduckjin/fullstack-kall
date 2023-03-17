package com.project.kall.repository;


import com.project.kall.entity.UserEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, String> {

    Optional<UserEntity> findById(String userId);

    //UserEntity findByIdAndPassword(String userId, String password);
    @Query("SELECT u FROM UserEntity u WHERE u.userId = :userId AND u.password = :password")
    UserEntity findByIdAndPassword(@Param("userId") String userId, @Param("password") String password);

    // 아이디 찾기
    UserEntity findByNameAndEmail(String name, String email);

    UserEntity findByNameAndPhoneNb(String name, String phoneNb);

    Optional<UserEntity> findByEmail(String email);


//    // 비밀번호 찾기 사용인데 태현이가 페이지 수정하면 그때 쓸거
//    UserEntity findByUserIdAndEmail(String userId, String email);
//
//    UserEntity findByUserIdAndPhone(String userId, String phoneNb);
}
