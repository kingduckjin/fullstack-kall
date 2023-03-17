//package com.project.kall.dto;
//
//import com.project.kall.entity.AdminEntity;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//
//import java.time.LocalDateTime;
//
//@Data
//@NoArgsConstructor
//public class AdminDTO {
//    private String userId;
//    private String name;
//    private String password;
//    private String phoneNb;
//    private String email;
//    private String birthday;
//    private String root;
//    private String eventagree;
//    private String role;
//    private Integer mileage;
//    private LocalDateTime date;
//    private String status;
//    private String tmp_2;
//
//
//    public static AdminDTO toAdminDTO(AdminEntity adminEntity){
//        AdminDTO adminDTO =new AdminDTO();
//        adminDTO.setUserId(adminEntity.getUserId());
//        adminDTO.setName(adminEntity.getName());
//        adminDTO.setPassword(adminEntity.getPassword());
//        adminDTO.setPhoneNb(adminEntity.getPhoneNb());
//        adminDTO.setEmail(adminEntity.getEmail());
//        adminDTO.setBirthday(adminEntity.getBirthday());
//        adminDTO.setRoot(adminEntity.getRoot());
//        adminDTO.setEventagree(adminEntity.getEventagree());
//        adminDTO.setRole(adminEntity.getRole());
//        adminDTO.setMileage(adminEntity.getMileage());
//        adminDTO.setDate(adminEntity.getDate());
//        adminDTO.setStatus(adminEntity.getStatus());
//
//        return adminDTO;
//    }
//}
