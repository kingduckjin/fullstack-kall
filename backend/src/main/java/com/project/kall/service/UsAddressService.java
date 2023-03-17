package com.project.kall.service;

import com.project.kall.dto.UsAddressDTO;
import com.project.kall.entity.UsAddressEntity;
import com.project.kall.repository.UsAddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsAddressService {
    private final UsAddressRepository usAddressRepository;


    //━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ 주소추가 ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    @Transactional //배송지추가
    public Integer save(UsAddressDTO usAddressDTO) {
        System.out.println("★ServiceSave ★ usAddressDTO = " + usAddressDTO);
        UsAddressEntity usAddressEntity = UsAddressEntity.toUsAddressEntity(usAddressDTO);
        Integer saveAddress = usAddressRepository.save(usAddressEntity).getUsAddressId();
        return saveAddress;
    }

    //━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ 주소삭제 ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    public Integer delete(Integer addressId) {
        System.out.println("★ ServiceDelete ★ addressId = " + addressId);
        usAddressRepository.deleteById(addressId);
        return 1;
    }

    //━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ 주소업데이트 ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    public Integer update(UsAddressDTO usAddressDTO) {
        UsAddressEntity usAddressEntity = UsAddressEntity.toUsAddressEntity(usAddressDTO);
        usAddressRepository.save(usAddressEntity);
        return 1;
    }

    //━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ 주소디테일 ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    @Transactional //배송지 Detail
    public UsAddressDTO findById(Integer addressId) {
        Optional<UsAddressEntity> usAddressEntity = usAddressRepository.findById(addressId);
        UsAddressEntity address = new UsAddressEntity();
        if (usAddressEntity.isPresent()) {
            address = usAddressEntity.get();
        }
        UsAddressDTO usAddressDTO = UsAddressDTO.toUsAddressDTO(address);
        return usAddressDTO;
    }

    //━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ 전체 주소목록 ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    @Transactional
    public List<UsAddressDTO> findAll(String userId) {
        List<UsAddressEntity> usAddressEntities = usAddressRepository.findAll();
        List<UsAddressDTO> usAddressDTOList = new ArrayList<>();
        for (UsAddressEntity address : usAddressEntities) {
            if (userId.equals(address.getUserEntity().getUserId())) {
                usAddressDTOList.add(UsAddressDTO.toUsAddressDTO(address));
            }
        }
        return usAddressDTOList;
    }

    //━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ 기본배송지설정 ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    @Transactional
    public Integer defaultupdate(Integer addressId) {
        // addressId와 일치하는 데이터 findById로 찾기
        Optional<UsAddressEntity> usAddressEntity = usAddressRepository.findById(addressId);

        // optional에서 값이 존재하는 경우
        if (usAddressEntity.isPresent()) {
            UsAddressEntity findEntity = usAddressEntity.get();

            // user_id 가져오기
            String userId = findEntity.getUserEntity().getUserId();

            // user_id가 같은 다른 리스트들의 status를 "false"로 업데이트
            List<UsAddressEntity> addressList = usAddressRepository.findAllByUserEntityUserIdAndUsAddressIdNot(userId, addressId);
            for (UsAddressEntity entity : addressList) {
                entity.setStatus("false");
                usAddressRepository.save(entity);
                findEntity.setStatus("true");
            }
            return 1;
        } else {
            return 0;
        }
    }
}
