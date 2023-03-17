package com.project.kall.dto;

import com.project.kall.entity.UserEntity;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserDelDTO {

    private  UserDTO userDTO;
    private String text;
    private LocalDateTime date;
}
