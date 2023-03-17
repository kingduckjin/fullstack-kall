package com.project.kall.controller;


import com.project.kall.dto.ReviewCmtDTO;
import com.project.kall.dto.ReviewDTO;
import com.project.kall.entity.ReviewCmtEntity;
import com.project.kall.entity.ReviewEntity;
import com.project.kall.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/reviews")
@RequiredArgsConstructor
public class ReviewController {

    @Autowired
    ReviewService reviewService;

//    ---------------후기 전체 목록 _ 모든 리뷰 가져옴 ----------------------------------------------------------------------
    @GetMapping("")
    public List<ReviewDTO> reviewDTOList () {
        return reviewService.list();
    }

//    ---------------후기 상세 목록(후기 번호 기준) 개별 리뷰 가져옴 -----------------------------------------------------------
    // 개별 리뷰
    @GetMapping("/{id}") // id = reviewId
    public ReviewDTO reviewDetail(@PathVariable Integer id) {
        return reviewService.reviewDetail(id);
    }

//    -----------------------------------리뷰 수정--------------------------------------------------------
    @PutMapping("/{id}") // id =reviewcmtId
    public ReviewDTO reviewupdate(@PathVariable Integer id, @RequestBody ReviewDTO reviewDTO) {
        reviewService.reviewUpdate(reviewDTO);
        return reviewService.reviewDetail(id);
    }

//    -----------------------------------리뷰 삭제--------------------------------------------------------
    @DeleteMapping("/{id}") // id = reviewId
    public List<ReviewDTO> reviewDelete (@PathVariable Integer id) {
        reviewService.delete(id);
        return reviewService.list();
    }

}
