//package com.project.kall.service;
//
//import com.project.kall.dto.AdminDTO;
//import com.project.kall.entity.AdminEntity;
//import com.project.kall.entity.UserDelEntity;
//import com.project.kall.entity.UserEntity;
//import com.project.kall.repository.AdminRepository;
//import com.project.kall.repository.UserDelRepository;
//import com.project.kall.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//@Service
//@RequiredArgsConstructor
//public class AdminService {
//
//    private final AdminRepository adminRepository;
//
//    private final UserRepository userRepository;
//    private final UserDelRepository userDelRepository;
//
//    public AdminDTO findById(String userId){
//        Optional<AdminEntity> optionalAdminEntity = adminRepository.findById(userId);
//        if(optionalAdminEntity.isPresent()){
//            AdminEntity adminEntity = optionalAdminEntity.get();
//            return AdminDTO.toAdminDTO(adminEntity);
//        }else{
//            return null;
//        }
//    }
//
//    public List<AdminDTO> findAll() {
//        List<AdminEntity> adminEntityList = adminRepository.findAll();
//        List<AdminDTO> adminDTOList = new ArrayList<>();
//        for(AdminEntity admin:adminEntityList){
//            adminDTOList.add(AdminDTO.toAdminDTO(admin));
//        }
//        return adminDTOList;
//    }
//
//}
