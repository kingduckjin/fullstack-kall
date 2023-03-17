package com.project.kall.controller;

import com.project.kall.dto.ReviewCmtDTO;
import com.project.kall.service.UserReviewCmtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviewcomments")
@RequiredArgsConstructor
public class UserReviewCmtController {
    //★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★ 답글 ★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★
   private final UserReviewCmtService userReviewCmtService;

    @GetMapping("/review/{id}") // 해당 게시글에 대한 리뷰답글 리스트
    public List<ReviewCmtDTO> cmtFindAll(@PathVariable("id") Integer reviewId) {
        System.out.println("★ 해당리뷰글의 댓글 출력 ★");
        return userReviewCmtService.cmtFindAll(reviewId);
    }

    @GetMapping("/{id}") // 답글 디테일
    public ReviewCmtDTO cmtFindById(@PathVariable("id") Integer reviewCmtId) {
        System.out.println("★ 답글 디테일 ★");
        return userReviewCmtService.cmtFindById(reviewCmtId);
    }

    @PostMapping("") // id값의 리뷰에 댓글작성하기
    public String cmtSave(@RequestBody ReviewCmtDTO reviewCmtDTO) {
        if (userReviewCmtService.cmtSave(reviewCmtDTO) > 0) {
            return "ok";
        } else {
            return "failed";
        }
    }

    @DeleteMapping("/{id}")
    public String cmtDelete(@PathVariable("id") Integer reviewCmtId){
        System.out.println("★ 답글 삭제 ★");
        if(userReviewCmtService.cmtDelete(reviewCmtId) > 0){
            return "ok";
        }else {
            return "failed";
        }
    }
    @PutMapping("/{id}")
    public String cmtUpdate(@PathVariable("id") Integer reviewCmtId,@RequestBody ReviewCmtDTO reviewCmtDTO){
        System.out.println("★ 답글 수정 ★");
        if (userReviewCmtService.cmtUpdate(reviewCmtDTO)>0){
            return "ok";
        }else {
            return "failed";
        }
    }
}
