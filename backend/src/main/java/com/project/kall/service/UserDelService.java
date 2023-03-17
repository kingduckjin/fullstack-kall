package com.project.kall.service;


import com.project.kall.dto.UserDTO;
import com.project.kall.entity.UserDelEntity;
import com.project.kall.entity.UserEntity;
import com.project.kall.repository.UserDelRepository;
import com.project.kall.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDelService {

    private final UserRepository userRepository;
    private final UserDelRepository userDelRepository;
    @Autowired
    UserService userService;
    @Autowired
    UserDelService userDelService;


    public void saveDeletedUser(UserDelEntity userDelEntity) {
        System.out.println(userDelEntity);
        userDelRepository.save(userDelEntity);
    }

    public void deleteI(String[] userIds) {
        List<String> userIdL = new ArrayList<>(Arrays.asList(userIds));
        for(String userId : userIdL) {
            if(userId != null) {
                System.out.println(userId);
                UserEntity userEntity = userService.loadUserByUserId(userId);
                userEntity.setStatus("탈퇴회원");
                userRepository.save(userEntity);

                UserDelEntity userDelEntity = new UserDelEntity();
                userDelEntity.setUserEntity(userEntity);
                userDelEntity.setUserId(userId);
                userDelEntity.setText("관리자에 의한 삭제");
                userDelService.saveDeletedUser(userDelEntity);
            }
        }

    }
}
