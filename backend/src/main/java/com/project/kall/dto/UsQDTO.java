package com.project.kall.dto;

import com.project.kall.entity.UsQEntity;
import lombok.*;
import java.time.LocalDateTime;

@Getter@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UsQDTO { // 관리자 1:1 데이터 전달

    private Integer usQId;
    private UserDTO userDTO;
    private CategoryDTO categoryDTO;
    private String title;
    private String content;
    private String comment;
    private LocalDateTime date1;
    private LocalDateTime date2;

    public UsQDTO(Integer usQId, UserDTO userDTO, CategoryDTO categoryDTO, String title, String content, LocalDateTime date1) {
        this.usQId = usQId;
        this.userDTO = userDTO;
        this.categoryDTO = categoryDTO;
        this.title = title;
        this.content = content;
        this.date1 = date1;
    }

    public static UsQDTO toUsQDTO(UsQEntity usQEntity) {
        if(usQEntity == null) return null;

        UsQDTO usQDTO = new UsQDTO();

        usQDTO.setUsQId(usQEntity.getUsQId());
        usQDTO.setUserDTO(UserDTO.toUserDTO(usQEntity.getUserEntity()));
        usQDTO.setCategoryDTO(CategoryDTO.toCategoryDTO(usQEntity.getCategoryEntity()));
        usQDTO.setTitle(usQEntity.getTitle());
        usQDTO.setContent(usQEntity.getContent());
        usQDTO.setComment(usQEntity.getComment());
        usQDTO.setDate1(usQEntity.getDate1());
        usQDTO.setDate2(usQEntity.getDate2());
        return usQDTO;
    }
}
