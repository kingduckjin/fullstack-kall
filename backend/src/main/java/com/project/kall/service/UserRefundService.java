package com.project.kall.service;


import com.project.kall.dto.OrderDetailDTO;
import com.project.kall.dto.RefundDTO;
import com.project.kall.dto.ReviewDTO;
import com.project.kall.entity.OrderDetailEntity;
import com.project.kall.entity.RefundEntity;
import com.project.kall.repository.OrderDetailRepository;
import com.project.kall.repository.RefundRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserRefundService {

    private final RefundRepository refundRepository;
    private final OrderDetailRepository orderDetailRepository;

    //━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ 해당유저의 환불목록 ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    public List<RefundDTO> findAll(String userId) {
        List<RefundEntity> refundEntities = refundRepository.findAll();
        List<RefundDTO> refundDTOList = new ArrayList<>();
        for (RefundEntity refund : refundEntities) {
            if (userId.equals(refund.getUserEntity().getUserId())) {
                refundDTOList.add(RefundDTO.toRefundDTO(refund));
            }
        }
        return refundDTOList;
    }

    //━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ 해당 id 값의  환불 조회 ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    public RefundDTO findById(Integer refundId) {
        Optional<RefundEntity> refundEntity = refundRepository.findById(refundId);
        RefundEntity refund = new RefundEntity();
        if (refundEntity.isPresent()) {
            refund = refundEntity.get();
        }
        RefundDTO refundDTO = RefundDTO.toRefundDTO(refund);
        return refundDTO;
    }

    public RefundDTO save(RefundDTO refundDTO, MultipartFile file) {
        String uploadDir = "D:\\MTest\\study\\0310통합kall\\kall\\src\\main\\resources\\templates\\fileStore"; //파일 업로드 경로 설정
        String baseUrl = "http://localhost:8088"; // 웹 서버 주소
        String originalFileName = file.getOriginalFilename();
        String[] fileParts = originalFileName.split("\\.");
        String extension = fileParts[fileParts.length - 1];
        String filename =  refundDTO.getTitle() + "_" + originalFileName;
        String fileUrl = baseUrl + "/image/" + filename; // 이미지 파일의 URL
        Path targetLocation = Paths.get(uploadDir +"\\" + filename); //파일 경로

        RefundEntity refund = RefundEntity.toSetRefundEntity(RefundDTO.toRefundDTO(RefundEntity.toSetRefundEntity(refundDTO)));
        refund.setImage_1(fileUrl);
        refund = refundRepository.save(refund);
        System.out.println(refund);
        try {
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.toString();
        }
        Optional<OrderDetailEntity> orderEntity = orderDetailRepository.findById(refund.getOrderDetailEntity().getOrderDnb());
        OrderDetailEntity orderDetailEntity = new OrderDetailEntity();
        if (orderEntity.isPresent()) {
            orderDetailEntity = orderEntity.get();
            orderDetailEntity.setStatus("환불진행중");
            orderDetailRepository.save(orderDetailEntity);
        }
        return RefundDTO.toRefundDTO(refund);
    }

    public Integer save(RefundDTO refundDTO) {
        RefundEntity refundEntity = RefundEntity.toSetRefundEntity(refundDTO);
        Integer refund = refundRepository.save(refundEntity).getOrderDetailEntity().getOrderDnb();

        Optional<OrderDetailEntity> orderEntity = orderDetailRepository.findById(refund);
        OrderDetailEntity orderDetailEntity = new OrderDetailEntity();
        if (orderEntity.isPresent()) {
            orderDetailEntity = orderEntity.get();
            orderDetailEntity.setStatus("환불진행중");
            orderDetailRepository.save(orderDetailEntity);
        }
        return refund;
    }
}
