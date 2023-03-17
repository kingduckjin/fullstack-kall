package com.project.kall.service;


import com.project.kall.dto.ReviewCmtDTO;
import com.project.kall.entity.ReviewCmtEntity;
import com.project.kall.repository.ReviewCmtRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserReviewCmtService {

    private final ReviewCmtRepository reviewCmtRepository;

    //━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ 해당 게시글의 답글 리스트 ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    public List<ReviewCmtDTO> cmtFindAll(Integer reviewId) {
        List<ReviewCmtEntity> reviewCmtEntities = reviewCmtRepository.findAll();
        List<ReviewCmtDTO> cmtDTOList = new ArrayList<>();
        for (ReviewCmtEntity reviewCmt : reviewCmtEntities) {
            if (reviewId == reviewCmt.getReviewEntity().getReviewId()) {
                cmtDTOList.add(ReviewCmtDTO.toReviewCmtDTO(reviewCmt));
            }
        }
        return cmtDTOList;
    }

    //━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ 해당 게시글의 답글 디테일 ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    public ReviewCmtDTO cmtFindById(Integer reviewCmtId) {
        Optional<ReviewCmtEntity> reviewCmtEntity = reviewCmtRepository.findById(reviewCmtId);
        ReviewCmtEntity reviewCmt = new ReviewCmtEntity();
        if (reviewCmtEntity.isPresent()) {
            reviewCmt = reviewCmtEntity.get();
        }
        ReviewCmtDTO reviewCmtDTO = ReviewCmtDTO.toReviewCmtDTO(reviewCmt);
        return reviewCmtDTO;
    }


    //━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ 리뷰답글 작성 ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    public Integer cmtSave(ReviewCmtDTO reviewCmtDTO) {
        ReviewCmtEntity cmt = ReviewCmtEntity.toReviewCmtEntity(reviewCmtDTO);
        reviewCmtRepository.save(cmt);
        return 1;
    }

    //━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ 리뷰답글 삭제 ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    public Integer cmtDelete(Integer reviewCmtId) {
        System.out.println("★ Service ★ 리뷰답글삭제");
        reviewCmtRepository.deleteById(reviewCmtId);
        return 1;
    }

    //━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ 리뷰답글 수정 ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    public Integer cmtUpdate(ReviewCmtDTO reviewCmtDTO) {
        System.out.println("★ Service ★ 리뷰답글수정");
        ReviewCmtEntity reviewCmt = ReviewCmtEntity.toReviewCmtEntity(reviewCmtDTO);
        reviewCmt.setDate(LocalDateTime.now());
        reviewCmtRepository.save(reviewCmt);
        return 1;
    }
}
