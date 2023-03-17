package com.project.kall.entity;

import com.project.kall.dto.CategoryDTO;
import com.project.kall.dto.NoticeDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter@Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@Table(name = "notice")
public class NoticeEntity {

    @Id
    @Column(name = "id")@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer noticeId;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private CategoryEntity categoryEntity;
    @Column(nullable = false, length = 50)
    private String title;
    @Column(nullable = false, length = 1000)
    private String content;
    @CreationTimestamp
    private LocalDateTime date = LocalDateTime.now();
    @ColumnDefault("0")
    @Column(length = 5)
    private int cnt;

    @Column(length = 50)
    private String tmp_1;
    @Column(length = 50)
    private String tmp_2;

    public static NoticeEntity toNoticeEntity(NoticeDTO noticeDTO) {
        if(noticeDTO == null) return null;
        NoticeEntity noticeEntity = new NoticeEntity();

        noticeEntity.setNoticeId(noticeDTO.getNoticeId());
        noticeEntity.setCategoryEntity(CategoryEntity.toCategoryEntity(noticeDTO.getCategoryDTO()));
        noticeEntity.setTitle(noticeDTO.getTitle());
        noticeEntity.setContent(noticeDTO.getContent());
        noticeEntity.setDate(noticeDTO.getDate());
        noticeEntity.setCnt(noticeDTO.getCnt());

        return noticeEntity;
    }
}
