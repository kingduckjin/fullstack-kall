package com.project.kall.dto;

import com.project.kall.entity.UsAddressEntity;
import com.project.kall.entity.UserEntity;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UsAddressDTO {

    private Integer usAddressId;
    private UserDTO userDTO;
    private String status;
    private String name;
    private String addressName;
    private String phone_nb;
    private String zip_code;
    private String address_1;
    private String address_2;

    public UsAddressDTO(String status, String name, String addressName, String phone_nb, String zip_code, String address_1, String address_2, UserDTO userDTO) {
        this.status = status;
        this.name = name;
        this.addressName = addressName;
        this.phone_nb = phone_nb;
        this.zip_code = zip_code;
        this.address_1 = address_1;
        this.address_2 = address_2;
        this.userDTO = userDTO;
    }

    //━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ 변환설정 ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    public static UsAddressDTO toUsAddressDTO(UsAddressEntity usAddressEntity) {
        if (usAddressEntity == null) {
            return null;
        }
        UsAddressDTO usAddressDTO = new UsAddressDTO();

        usAddressDTO.setUsAddressId(usAddressEntity.getUsAddressId());
        usAddressDTO.setStatus(usAddressEntity.getStatus());
        usAddressDTO.setName(usAddressEntity.getName());
        usAddressDTO.setAddressName(usAddressEntity.getAddressName());
        usAddressDTO.setPhone_nb(usAddressEntity.getPhone_nb());
        usAddressDTO.setZip_code(usAddressEntity.getZip_code());
        usAddressDTO.setAddress_1(usAddressEntity.getAddress_1());
        usAddressDTO.setAddress_2(usAddressEntity.getAddress_2());
        usAddressDTO.setUserDTO(UserDTO.toUserDTO(usAddressEntity.getUserEntity()));
        System.out.println("DTO > toUsAddressDTO");
        return usAddressDTO;
    }
}

