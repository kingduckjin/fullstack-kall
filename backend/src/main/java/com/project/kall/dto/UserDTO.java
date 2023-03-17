package com.project.kall.dto;

import com.project.kall.entity.UsQEntity;
import com.project.kall.entity.UserEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String userId;
    private String name;
    private String password;
    private String phoneNb;
    private String email;
    private String birthday;
    private String root;
    private String eventagree;
    private String role;
    private Integer mileage;
    private LocalDateTime date;
    private String status;

    public UserDTO(UserEntity userEntity) {

        this.userId=userEntity.getUserId();
        this.name = userEntity.getName();
        this.password = userEntity.getPassword();
        this.phoneNb = userEntity.getPhoneNb();
        this.email = userEntity.getEmail();
        this.birthday = userEntity.getBirthday();
        this.root = userEntity.getRoot();
        this.eventagree = userEntity.getEventagree();
        this.role = userEntity.getRole();
        this.mileage = userEntity.getMileage();
        this.date = userEntity.getDate();
        this.status = userEntity.getStatus();
    }


    public static UserDTO toUserDTO(UserEntity userEntity) {
        if(userEntity == null) return null;
        UserDTO userDTO = new UserDTO();

        userDTO.setUserId(userEntity.getUserId());
        userDTO.setName(userEntity.getName());
        userDTO.setPassword(userEntity.getPassword());
        userDTO.setPhoneNb(userEntity.getPhoneNb());
        userDTO.setEmail(userEntity.getEmail());
        userDTO.setBirthday(userEntity.getBirthday());
        userDTO.setRoot(userEntity.getRoot());
        userDTO.setEventagree(userEntity.getEventagree());
        userDTO.setRole(userEntity.getRole());
        userDTO.setMileage(userEntity.getMileage());
        userDTO.setDate(userEntity.getDate());
        userDTO.setStatus(userEntity.getStatus());
        return userDTO;
    }

    public static UserDTO toUserDTOnopass(UserEntity userEntity) {
        if(userEntity == null) return null;
        UserDTO userDTO = new UserDTO();

        userDTO.setUserId(userEntity.getUserId());
        userDTO.setName(userEntity.getName());
        userDTO.setPassword(userEntity.getPassword());
        userDTO.setPhoneNb(userEntity.getPhoneNb());
        userDTO.setEmail(userEntity.getEmail());
        userDTO.setBirthday(userEntity.getBirthday());
        userDTO.setRoot(userEntity.getRoot());
        userDTO.setEventagree(userEntity.getEventagree());
        userDTO.setRole(userEntity.getRole());
        userDTO.setMileage(userEntity.getMileage());
        userDTO.setDate(userEntity.getDate());
        userDTO.setStatus(userEntity.getStatus());
        return userDTO;
    }
}
