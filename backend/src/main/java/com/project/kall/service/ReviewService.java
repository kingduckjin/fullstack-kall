package com.project.kall.service;

import com.project.kall.dto.ReviewCmtDTO;
import com.project.kall.dto.ReviewDTO;
import com.project.kall.entity.ReviewCmtEntity;
import com.project.kall.entity.ReviewEntity;
import com.project.kall.repository.ReviewCmtRepository;
import com.project.kall.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class ReviewService {

    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    ReviewCmtRepository reviewCmtRepository;

    //    -----------------------------------후기 전체 목록 --------------------------------------------------------
    public List<ReviewDTO> list() {
        List<ReviewEntity> reviewEntityList = reviewRepository.findAllByOrderByReviewIdDesc();
        List<ReviewDTO> reviewDTOList = new ArrayList<>();
        for(ReviewEntity reviewEntity : reviewEntityList) {
            if(reviewEntity != null) {
                reviewDTOList.add(ReviewDTO.toReviewDTO(reviewEntity));
            }
        }
        return reviewDTOList;
    }

    //    -----------------------------------후기 상세 목록(후기 번호 기준) --------------------------------------------------------
    public ReviewDTO reviewDetail(Integer id) { // reviewId를 받아와서 찾고, 그 reviewDTO를 전달
        Optional<ReviewEntity> optionalReviewEntity = reviewRepository.findById(id);
        if(optionalReviewEntity.isPresent()) {
            ReviewEntity reviewEntity = optionalReviewEntity.get();
            ReviewDTO reviewDTO = ReviewDTO.toReviewDTO(reviewEntity);
            return reviewDTO;
        } else return  null;
    }
    public List<ReviewCmtDTO> reviewCommentL(Integer id) {
        ReviewDTO reviewDTO = reviewDetail(id); // 전달 받은 reviewId로 reviewDTO받음;
        Integer reviewId = reviewDTO.getReviewId();
        System.out.println(reviewId);
        List<ReviewCmtEntity> resultList = reviewCmtRepository.ReviewId(reviewId);
        List<ReviewCmtDTO> reviewCmtDTOList = new ArrayList<>();
        for(ReviewCmtEntity reviewEntity : resultList) {
            System.out.println(reviewEntity.getContent());
            if(reviewEntity != null) {
                reviewCmtDTOList.add(ReviewCmtDTO.toReviewCmtDTO(reviewEntity));
            }
        }
        return reviewCmtDTOList;
    }

    //    -----------------------------------리뷰 수정--------------------------------------------------------
    public void reviewUpdate(ReviewDTO reviewDTO) {
        ReviewEntity reviewEntity = ReviewEntity.toReviewEntity(reviewDTO);
        updatetime(reviewEntity);
    } // 저장을 시간 변경하면서 하는게 나을 것 같아서 아래에 넣음
    public void updatetime(ReviewEntity reviewEntity) {
        reviewEntity.setDate(LocalDateTime.now());
        reviewRepository.save(reviewEntity);
    } // date가 @CreateionTimeStamp라 수정 시점으로 저장할 수 있는 method 추가


//    -----------------------------------리뷰 삭제--------------------------------------------------------

    public void delete(Integer id) {
        reviewRepository.deleteById(id);
    }

    //    -----------------------------------리뷰 덧글 수정 (이건 지금 안됨) -------------------------------------------------------
//    public void updateDate(ReviewCmtEntity reviewCmtEntity) {
//        reviewCmtEntity.setDate(LocalDateTime.now());
//        reviewCmtRepository.save(reviewCmtEntity);
//    } // date가 @CreateionTimeStamp라 수정 시점으로 저장할 수 있는 method 추가
    public ReviewCmtDTO commentUpdate(ReviewCmtDTO reviewCmtDTO) {

        ReviewCmtEntity reviewCmtEntity = ReviewCmtEntity.toReviewCmtEntity(reviewCmtDTO);
        reviewCmtEntity.setDate(LocalDateTime.now());
        reviewCmtRepository.save(reviewCmtEntity);

        ReviewCmtDTO reviewCmt = ReviewCmtDTO.toReviewCmtDTO(reviewCmtEntity);
        return reviewCmt;
        // => toReviewCmtDTO에서
    }



    //    -----------------------------------리뷰 덧글 삭제--------------------------------------------------------
    public void deleteCmt(Integer id) {
        reviewCmtRepository.deleteById(id);
    }

//    public ReviewDTO updateImage(ReviewDTO reviewDTO, MultipartFile[] images) {
//        String Upload_path = "D:\\image"; // 업로드할 위치
//
//        try {
//            for (MultipartFile multipart : images) {
//                MultipartFile file = multipart;
//                String fileId = (reviewDTO.getProductDTO().getName()+"_"+(new Random().ints(1000, 9999).findAny().getAsInt()));
//                String originName = file.getOriginalFilename();
//                String fileExtention = originName.substring(originName.lastIndexOf(".")+1);
//                long fileSize = file.getSize();
//
//                File fileSave = new File(Upload_path, fileId+"."+fileExtention);
//                if(!fileSave.exists()) {
//                    fileSave.mkdirs();
//                }
//                file.transferTo(fileSave);
//
//                System.out.println("fileId = " + fileId);
//                System.out.println("orignName = " + originName);
//                System.out.println("fileExtension = " + fileExtention);
//                System.out.println("fileSize = " + fileSize);
//                Integer i = 1;
//                if(i == 1) {
//                    reviewDTO.setImage_1(Upload_path+fileId+"_"+fileExtention);
//                } else {
//                    reviewDTO.setImage_2(Upload_path+fileId+"_"+fileExtention);
//                }
//                ++i;
//            }
//        } catch(IOException e) {
//            return reviewDTO;
//        }
//        return reviewDTO;
//    }
}