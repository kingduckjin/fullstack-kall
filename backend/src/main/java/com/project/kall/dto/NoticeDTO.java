package com.project.kall.dto;

import com.project.kall.entity.CategoryEntity;
import com.project.kall.entity.NoticeEntity;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NoticeDTO {

    private Integer noticeId;
    private CategoryDTO categoryDTO;
    private String title;
    private String content;
    private LocalDateTime date;
    private int cnt;

    public static NoticeDTO toNoticeDTO(NoticeEntity noticeEntity) {
        if(noticeEntity == null) return null;
        NoticeDTO noticeDTO = new NoticeDTO();

        noticeDTO.setNoticeId(noticeEntity.getNoticeId());
        noticeDTO.setCategoryDTO(CategoryDTO.toCategoryDTO(noticeEntity.getCategoryEntity()));
        noticeDTO.setTitle(noticeEntity.getTitle());
        noticeDTO.setContent(noticeEntity.getContent());
        noticeDTO.setNoticeId(noticeEntity.getNoticeId());
        noticeDTO.setDate(noticeEntity.getDate());
        noticeDTO.setCnt(noticeEntity.getCnt());

        return noticeDTO;
    }
}
