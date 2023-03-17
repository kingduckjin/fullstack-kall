package com.project.kall.controller;


import com.project.kall.dto.ProductQDTO;
import com.project.kall.dto.ReviewCmtDTO;
import com.project.kall.dto.ReviewDTO;
import com.project.kall.entity.ReviewEntity;
import com.project.kall.service.ReviewService;
import com.project.kall.service.UserReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class UserReviewController {

    @Autowired
    UserReviewService userReviewService;

    //★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★ 리뷰 ★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★
    @GetMapping("/user/{user_id}") // 해당하는 유저가 갖고있는 리뷰목록출력
    public List<ReviewDTO> findAll(@PathVariable("user_id") String user_id) {
        System.out.println("★ Review List ★");
        return userReviewService.findAll(user_id);
    }

    @GetMapping("/{id}") // 해당리뷰디테일
    public ReviewDTO findById(@PathVariable("id") Integer reviewId) {
        System.out.println("★ Review Detail ★");
        return userReviewService.findById(reviewId);
    }

    @GetMapping("")
    public List<ReviewDTO> findAllforAll() {
        return userReviewService.findAllforAll();
    }

    @PostMapping("")
    public ResponseEntity<ReviewDTO> save(@RequestPart("reviewData") ReviewDTO reviewDTO, @RequestPart("files") MultipartFile file) throws IOException {
        ReviewEntity reviewEntity = ReviewEntity.toReviewEntity(userReviewService.saveFile(reviewDTO, file));
        return ResponseEntity.ok(ReviewDTO.toReviewDTO(reviewEntity));
    }

//    최종 상품 등록 컨트롤러 메서드 - [POST /products, {상품 데이터}] 상품 추가
//    @PostMapping("")
//    public ResponseEntity<ProductDTO> registerProduct(@RequestPart("productData") ProductDTO productDTO, @RequestPart("file") MultipartFile file) {
//            ProductEntity productEntity = ProductEntity.toSetProductEntity(productService.registerProduct(productDTO, file));
//            return ResponseEntity.ok(ProductDTO.toProductDTO(productEntity));
//            }

    @PutMapping("/{id}") // 유저가 리뷰 수정
    public String update(@PathVariable("id") Integer reviewId, @RequestBody ReviewDTO reviewDTO) {
        if (userReviewService.update(reviewDTO) > 0) {
            return "ok";
        } else {
            return "failed";
        }
    }

    @DeleteMapping("/{id}") //리뷰 삭제
    public String delete(@PathVariable("id") Integer reviewId) {
        userReviewService.delete(reviewId);
        return "ok";
    }

    @GetMapping("/product/{id}")
    public List<ReviewDTO> findAllProducts(@PathVariable("id") Integer productId) {
        return userReviewService.findAllproduct(productId);
    }

}
