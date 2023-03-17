package com.project.kall.service;

import com.project.kall.dto.UsQDTO;
import com.project.kall.entity.UsQEntity;
import com.project.kall.repository.UsQRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserUsQService {

    private final UsQRepository usQRepository;
    //━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ 유저의 1:1 목록 ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    @Transactional
    public List<UsQDTO> userfindAll(String userId) {
        List<UsQEntity> usQEntities = usQRepository.findAllByOrderByUsQIdDesc();
        List<UsQDTO> usQDTOList = new ArrayList<>();
        for (UsQEntity question : usQEntities) {
            if (userId.equals(question.getUserEntity().getUserId())) {
                usQDTOList.add(UsQDTO.toUsQDTO(question));
            }
        }
        return usQDTOList;
    }

    //━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ 해당 1:1 문의 디테일 ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    public UsQDTO userfindById(Integer usQId) {
        Optional<UsQEntity> usQEntity = usQRepository.findById(usQId);
        UsQEntity question = new UsQEntity();
        if (usQEntity.isPresent()) {
            question = usQEntity.get();
        }
        UsQDTO usQDTO = UsQDTO.toUsQDTO(question);
        return usQDTO;
    }

    //━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ 해당 1:1 문의글 수정 ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    public Integer userUpdate(UsQDTO usQDTO) {
        UsQEntity usQEntity = UsQEntity.toSaveEntity(usQDTO);
        usQEntity.setDate1(LocalDateTime.now());
        usQRepository.save(usQEntity);
        return 1;
    }


    //━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ 해당 1:1 문의글 삭제 ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    public Integer userDelete(Integer usQId) {
        usQRepository.deleteById(usQId);
        return 1;
    }


    //━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━  1:1 문의글 등록 ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    public Integer userSave(UsQDTO usQDTO) {
        UsQEntity usQEntity = UsQEntity.toSaveEntity(usQDTO);
        Integer question = usQRepository.save(usQEntity).getUsQId();
        return question;
    }


    public List<UsQDTO> findAllforAll() {
        List<UsQEntity> usQEntities = usQRepository.findAll();
        List<UsQDTO> usQDTOList = new ArrayList<>();
        for (UsQEntity usQ : usQEntities){
            usQDTOList.add(UsQDTO.toUsQDTO(usQ));
        }
        return usQDTOList;
    }
}
