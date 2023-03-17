package com.project.kall.service;

import com.project.kall.dto.CategoryDTO;
import com.project.kall.dto.UsQDTO;
import com.project.kall.dto.UserDTO;
import com.project.kall.entity.CategoryEntity;
import com.project.kall.entity.UsQEntity;
import com.project.kall.entity.UserEntity;
import com.project.kall.repository.UsQRepository;
import com.sun.tools.jconsole.JConsoleContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsQService { // 관리자 1:1

    @Autowired
    UsQRepository usQRepository;


    //    -----------------------------------문의글 리스트--------------------------------------------------------
    public List<UsQDTO> findAll() {
        List<UsQEntity> usQEntityList = usQRepository.findAll();
        List<UsQDTO> usQDTOList = new ArrayList<>();
        for (UsQEntity usQEntity : usQEntityList) {
            if (usQEntity != null) {
                System.out.println(usQEntity);
                usQDTOList.add(UsQDTO.toUsQDTO(usQEntity));
            }
        }
        return usQDTOList;
    }

    //    ----------------------------문의글 detail _ 개별 문의 가져옴 ---------------------------------------------------------
    public UsQDTO findById(Integer id) {
        Optional<UsQEntity> optionalUsQEntity = usQRepository.findById(id);
        if (optionalUsQEntity.isPresent()) {
            UsQEntity usQEntity = optionalUsQEntity.get();
            UsQDTO usQDTO = UsQDTO.toUsQDTO(usQEntity);
            return usQDTO;
        } else {
            return null;
        }
    }

    //    ----------------------------------문의글 삭제 --------------------------------------------------------
    public void delete(Integer id) {
        usQRepository.deleteById(id);
    }

    public void deletes(String[] list) {
        List<String> idList = new ArrayList<>(Arrays.asList(list));
        for (String id : idList) {
            if (id != null) {
                usQRepository.deleteByUserEntityUserId(id);
            }
        }
    }

    public void update(UsQDTO usQDTO) {
        System.out.println("ddd"+usQDTO.getComment());
        System.out.println("ddd"+usQDTO.getContent());
        UsQEntity usQEntity = UsQEntity.toSaveEntity(usQDTO);
        usQRepository.save(usQEntity);
    }

    //    ----------------------------- 해당 유저의 모든 문의 가져오기 ----------------------------------------------------------
    public List<UsQDTO> finduserQ(String userId) {
        List<UsQEntity> usQEntity = usQRepository.userId(userId);
        List<UsQDTO> usQDTOList = new ArrayList<>();
        for (UsQEntity usq : usQEntity) {
            if (usq != null) {
                usQDTOList.add(UsQDTO.toUsQDTO(usq));
            }
        }
        return usQDTOList;
    }

    //    -----------------------------------카테고리 가져오기 ----------------------------------------------------------------
    public CategoryDTO getCategory(Integer id) { // usqId
        Optional<UsQEntity> optionalusQEntity  = usQRepository.findById(id);
        System.out.println(id);
        if (optionalusQEntity.isEmpty()) {
            System.out.println("무야..");
            return null;
        } else {
            UsQEntity usQEntity = optionalusQEntity.get();
            System.out.println(usQEntity.getUsQId());
            CategoryEntity categoryEntity = usQEntity.getCategoryEntity();
            CategoryDTO categoryDTO = CategoryDTO.toCategoryDTO(categoryEntity);
            System.out.println(categoryDTO.getCategoryId());
            return categoryDTO;
        }
    }

    public UserDTO getUser(Integer id) {
        Optional<UsQEntity> optionalusQEntity  = usQRepository.findById(id);
        System.out.println(id);
        if (optionalusQEntity.isEmpty()) {
            return null;
        } else {
            UsQEntity usQEntity = optionalusQEntity.get();
            System.out.println(usQEntity.getUsQId());
            UserEntity userEntity = usQEntity.getUserEntity();
            UserDTO userDTO = UserDTO.toUserDTO(userEntity);
            return userDTO;
        }
    }
}

//    public void deleteId( id) {
//        usQRepository.deleteById(id);
//    }

//    -----------------------------------답글 입력--------------------------------------------------------
//    public void save(UsQDTO usQDTO) throws IOException {
//        UsQEntity usQEntity = UsQEntity.toSaveEntity(usQDTO);
//        usQRepository.save(usQEntity);
//    }

//    public void commDel(UsQDTO usQDTO) {
//        UsQEntity usQEntity = UsQEntity.toCommDelEntity(usQDTO);
//        usQRepository.save(usQEntity);
//    }

//}
