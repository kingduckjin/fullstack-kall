package com.project.kall.controller;


import com.project.kall.dto.RefundDTO;
import com.project.kall.dto.ReviewDTO;
import com.project.kall.entity.RefundEntity;
import com.project.kall.entity.ReviewEntity;
import com.project.kall.service.UserRefundService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/refunds")
@RequiredArgsConstructor
public class UserRefundController {
    private final UserRefundService userRefundService;

    @GetMapping("/user/{user_id}")
    public List<RefundDTO> findAll(@PathVariable("user_id") String userId) {
        return userRefundService.findAll(userId);
    }

    @GetMapping("/{id}")
    public RefundDTO findById(@PathVariable("id") Integer refundId) {
        return userRefundService.findById(refundId);
    }


    @PostMapping("")
    public ResponseEntity<RefundDTO> save(@RequestPart("refundData") RefundDTO refundDTO, @RequestPart("files") MultipartFile file){
        RefundEntity refundEntity = RefundEntity.toSetRefundEntity(userRefundService.save(refundDTO,file));
        return ResponseEntity.ok(RefundDTO.toRefundDTO(refundEntity));
    }

}
