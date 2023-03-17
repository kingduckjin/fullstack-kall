package com.project.kall.service;

import com.project.kall.dto.ProductQDTO;
import com.project.kall.dto.ReviewDTO;
import com.project.kall.entity.ProductQEntity;
import com.project.kall.entity.ReviewEntity;
import com.project.kall.repository.ReviewCmtRepository;
import com.project.kall.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.jni.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserReviewService {
    @Autowired
    ReviewRepository reviewRepository;

    //━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ 유저 리뷰 목록 ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    public List<ReviewDTO> findAll(String userId) {
        List<ReviewEntity> reviewEntities = reviewRepository.findAll();
        List<ReviewDTO> reviewDTOList = new ArrayList<>();
        for (ReviewEntity review : reviewEntities) {
            if (userId.equals(review.getUserEntity().getUserId())) {
                reviewDTOList.add(ReviewDTO.toReviewDTO(review));
            }
        }
        return reviewDTOList;
    }

    //━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ 유저 리뷰 디테일 ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    public ReviewDTO findById(Integer reviewId) {
        Optional<ReviewEntity> reviewEntity = reviewRepository.findById(reviewId);
        ReviewEntity review = new ReviewEntity();
        if (reviewEntity.isPresent()) {
            review = reviewEntity.get();
        }
        ReviewDTO reviewDTO = ReviewDTO.toReviewDTO(review);
        return reviewDTO;
    }

    //━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ 유저 리뷰 업데이트 ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    public Integer update(ReviewDTO reviewDTO) {
        System.out.println("★Service★ Review Update");
        ReviewEntity reviewEntity = ReviewEntity.toReviewEntity(reviewDTO);
        reviewEntity.setDate(LocalDateTime.now());
        reviewRepository.save(reviewEntity);
        return 1;
    }

    //━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ 유저 리뷰 삭제 ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    public void delete(Integer reviewId) {
        reviewRepository.deleteById(reviewId);
    }

    //    ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ 유저 리뷰 작성 ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    @Transactional
    public ReviewDTO saveFile(ReviewDTO reviewDTO, MultipartFile file) {
        String uploadDir = "D:/MTest/study/testfile/src/images/filestore"; //파일 업로드 경로 설정
        String originalFileName = file.getOriginalFilename();
        String[] fileParts = originalFileName.split("/.");
        String extension = fileParts[fileParts.length - 1];
        String filename = 123 + "_" + originalFileName;
        String fileUrl = "/src/images/filestore" + filename; // 이미지 파일의 URL
        Path targetLocation = Paths.get(uploadDir + "/" + filename); //파일 경로

        ReviewEntity review = ReviewEntity.toReviewEntity(ReviewDTO.toReviewDTO(ReviewEntity.toReviewEntity(reviewDTO)));
        review.setImage_1(fileUrl);
        review = reviewRepository.save(review);
        System.out.println(review);

        try {
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.toString();
        }
        return ReviewDTO.toReviewDTO(review);
    }

    //━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ 해당하는 상품의 리뷰조회 ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    public List<ReviewDTO> findAllproduct(Integer productId) {
        List<ReviewEntity> reviewEntities = reviewRepository.findAll();
        List<ReviewDTO> review = new ArrayList<>();

        for (ReviewEntity reviewEntity : reviewEntities) {
            if (productId == reviewEntity.getProductEntity().getProductId()) {
                review.add(ReviewDTO.toReviewDTO(reviewEntity));
            }
        }
        return review;
    }

    public List<ReviewDTO> findAllforAll() {
        List<ReviewEntity> reviewEntities = reviewRepository.findAll();
        List<ReviewDTO> reviewDTOList = new ArrayList<>();
        for (ReviewEntity review : reviewEntities){
            reviewDTOList.add(ReviewDTO.toReviewDTO(review));
        }
        return reviewDTOList;
    }
}
