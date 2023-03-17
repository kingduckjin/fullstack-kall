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
@RequestMapping("/admin/reviews/comment/{id}")
@RequiredArgsConstructor
public class ReviewCommentController {

    @Autowired
    ReviewService reviewService;


    //    ---------------후기 상세 목록(후기 번호 기준) 개별 리뷰 가져옴 -----------------------------------------------------------
    // 리뷰에 대한 답글 리스트 (답글 id를 기준으로 정렬하여 최신순이 올라가게..!)
    @GetMapping("")  //=> id = reviewId
    public List<ReviewCmtDTO> reviewCmtEntityList(@PathVariable Integer id) {
        return reviewService.reviewCommentL(id);
    }

    //    -----------------------------------리뷰 덧글 수정--------------------------------------------------------
    @PutMapping("") // id =reviewcmtId
    public ReviewCmtDTO commentUpdate(@RequestBody ReviewCmtDTO reviewCmtDTO){
        return reviewService.commentUpdate(reviewCmtDTO); // get값 전달해주면 id 받을 필요없고..
    }

    //    -----------------------------------리뷰 덧글 삭제--------------------------------------------------------
    @DeleteMapping("") // id =reviewcmtId
    public void reviewCmtDelete(@PathVariable Integer id) {
        reviewService.deleteCmt(id);
    }
}
