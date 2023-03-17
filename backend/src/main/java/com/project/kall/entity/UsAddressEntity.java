package com.project.kall.entity;

import com.project.kall.dto.UsAddressDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@Table(name = "us_address")
public class UsAddressEntity {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer usAddressId;

    @JoinColumn(name = "userId")
    @ManyToOne
    private UserEntity userEntity;

    @Column(length = 10)
    private String status;
    @Column(nullable = false, length = 10)
    private String name;
    @Column(length = 30)
    private String addressName;
    @Column(nullable = false, length = 15)
    private String phone_nb;
    @Column(nullable = false, length = 10)
    private String zip_code;
    @Column(nullable = false, length = 100)
    private String address_1;
    @Column(length = 100)
    private String address_2;

    @Column(length = 50)
    private String tmp_1;
    @Column(length = 50)
    private String tmp_2;

    //━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ 변환설정 ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    public static UsAddressEntity toUsAddressEntity(UsAddressDTO usAddressDTO) {
        UsAddressEntity usAddressEntity = new UsAddressEntity();

        usAddressEntity.setUsAddressId(usAddressDTO.getUsAddressId());
        usAddressEntity.setStatus(usAddressDTO.getStatus());
        usAddressEntity.setName(usAddressDTO.getName());
        usAddressEntity.setAddressName(usAddressDTO.getAddressName());
        usAddressEntity.setPhone_nb(usAddressDTO.getPhone_nb());
        usAddressEntity.setZip_code(usAddressDTO.getZip_code());
        usAddressEntity.setAddress_1(usAddressDTO.getAddress_1());
        usAddressEntity.setAddress_2(usAddressDTO.getAddress_2());
        usAddressEntity.setUserEntity(UserEntity.toUserEntity(usAddressDTO.getUserDTO()));

        return usAddressEntity;
    }
}
