package com.project.kall.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@Table(name = "us_del")
public class UserDelEntity implements Serializable {

    @Id
    @Column(name = "id", length = 15)
    private  String userId;
    @JoinColumn(name = "id", referencedColumnName = "id")
    @MapsId
    @ManyToOne
    private UserEntity userEntity;

    @Column(length = 500)
    private String text;
    @CreationTimestamp
    private LocalDateTime date = LocalDateTime.now();
    public void setUserEntity(UserEntity userEntity) {
        if(userEntity == null) {
            return;
        }
        this.userEntity = userEntity;
        this.userId = userEntity.getId();
    }

}
