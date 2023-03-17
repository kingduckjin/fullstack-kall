package com.project.kall.entity;


import com.project.kall.dto.UsQDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@Table(name = "us_q")
public class UsQEntity {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer usQId;

    @JoinColumn(name = "userId")
    @ManyToOne
    private UserEntity userEntity;

    @JoinColumn(name = "categoryId")
    @ManyToOne
    private CategoryEntity categoryEntity;

    @Column(length = 50, nullable = false)
    private String title;
    @Column(length = 500, nullable = false)
    private String content;
    @Column(length = 500)
    private String comment;
    @CreationTimestamp
    private LocalDateTime date1 = LocalDateTime.now();

    @CreationTimestamp
    private LocalDateTime date2 = LocalDateTime.now();


    public static UsQEntity toSaveEntity(UsQDTO usQDTO) {
        if(usQDTO == null) return null;
        UsQEntity usQEntity = new UsQEntity();

        usQEntity.setUsQId(usQDTO.getUsQId());
        usQEntity.setCategoryEntity(CategoryEntity.toCategoryEntity(usQDTO.getCategoryDTO()));
        usQEntity.setUserEntity(UserEntity.toUserEntity(usQDTO.getUserDTO()));
        usQEntity.setTitle(usQDTO.getTitle());
        usQEntity.setContent(usQDTO.getContent());
        usQEntity.setComment(usQDTO.getComment());
        usQEntity.setDate1(usQDTO.getDate1());
        usQEntity.setDate2(LocalDateTime.now());
        return usQEntity;
    }
    public static UsQEntity toUpdateEntity(UsQDTO usQDTO) {
        UsQEntity usQEntity = new UsQEntity();
        usQEntity.setUsQId(usQDTO.getUsQId());
        usQEntity.setCategoryEntity(CategoryEntity.toCategoryEntity(usQDTO.getCategoryDTO()));
        usQEntity.setUserEntity(UserEntity.toUserEntity(usQDTO.getUserDTO()));
        usQEntity.setTitle(usQDTO.getTitle());
        usQEntity.setContent(usQDTO.getContent());
        usQEntity.setComment(usQDTO.getComment());
        usQEntity.setDate1(usQDTO.getDate1());
        usQEntity.setDate2(usQDTO.getDate2());
        return usQEntity;
    }

}

