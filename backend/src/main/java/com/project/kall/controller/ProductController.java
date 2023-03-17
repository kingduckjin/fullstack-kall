package com.project.kall.controller;

import com.project.kall.dto.ProductDTO;
import com.project.kall.dto.ProductImgDTO;
import com.project.kall.entity.ProductEntity;
import com.project.kall.entity.ProductImgEntity;
import com.project.kall.repository.ProductImgRepository;
import com.project.kall.repository.ProductRepository;
import com.project.kall.service.ProductImgService;
import com.project.kall.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
@RequestMapping("/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductImgService productImgService;

    private ProductImgRepository productImgRepository;
    //최종 상품 등록 컨트롤러 메서드 - [POST /products, {상품 데이터}] 상품 추가
    @PostMapping("")
    public ResponseEntity<ProductDTO> registerProduct( HttpServletRequest request, @RequestPart("productData") ProductDTO productDTO, @RequestPart("file") MultipartFile file) {
        ProductEntity productEntity = ProductEntity.toSetProductEntity(productService.registerProduct(productDTO, file));
        return ResponseEntity.ok(ProductDTO.toProductDTO(productEntity));
    }

    // 최종 상품 수정 메인사진 - [PUT /products/{id}/thumnail, {이미지 데이터}] 대표 이미지 수정
    @PutMapping("/{productId}/thumnail")
    public ResponseEntity<ProductDTO> updateProductImage(@PathVariable Integer productId, @RequestParam("file") MultipartFile file) {
        ProductDTO productDTO = productService.updateProductImage(productId, file);
        return ResponseEntity.ok(productDTO);
    }


    // 최종 상품 수정 나머지 - [PUT /products/{id}/info, {기본 정보 데이터}] 기본 정보 수정
    @PutMapping("/{id}/info")
    public ProductDTO updateProductExceptImage(@PathVariable("id") Integer productId, @RequestBody ProductDTO productDTO) {
        productDTO.setProductId(productId);
        return productService.updateProductExceptImage(productId,productDTO);
    }


//    //상세 이미지 등록 컨트롤러 메서드
//    @PostMapping("/product-images")
//    public ResponseEntity<List<ProductImgDTO>> registerProductImages(@RequestParam("productId") Integer productId,
//                                                                     @RequestParam("files") List<MultipartFile> files) {
//        List<ProductImgDTO> productImgDTOList = productImgService.saveProductImages(productId, files);
//        return ResponseEntity.ok(productImgDTOList);
//    }

    // 상세 이미지 수정 - [PUT /products/{id}/images, {이미지 데이터 배열}] 상세 이미지 수정
    @PostMapping("/{productId}/images")
    public ResponseEntity<List<ProductImgDTO>> registerProductImages(@PathVariable("productId") Integer productId, @RequestParam("file") List<MultipartFile> files) {
        System.out.println(productId);
        System.out.println("images: " + files);
        List<ProductImgDTO> productImgDTOList = productImgService.saveProductImages(productId, files);
        return ResponseEntity.ok(productImgDTOList);
    }


    // 최종 메인에 최신상품 6개 쏘기  GET /products/recent - 최근에 등록된 데이터순으로 6개 데이터
    @GetMapping("/recent")
    public ResponseEntity<List<ProductDTO>> recentProducts() {
        List<ProductDTO> recentProductDTOs = productService.recentProducts();
        return ResponseEntity.ok(recentProductDTOs);
    }


    // 최종 상품 검색GET /products?keyword={검색어}
    @GetMapping(params = "keyword")
    public List<ProductDTO> searchProducts(@RequestParam String keyword) {
        return productService.searchProducts(keyword);
    }

    //  -----최종 카테고리 값으로 제작 / 디자인 / etc 구분하기
   @GetMapping("/category/custom")
    public List<ProductDTO> customProduct() {
        return productService.customerProduct();
    }

    @GetMapping("/category/design")
    public List<ProductDTO> designProduct() {
        return productService.designProduct();
    }

    @GetMapping("/category/etc")
    public List<ProductDTO> etcProduct() {
        return productService.etcProduct();
    }


    //최종 상품  카테고리 구분 없이 목록 조회됨 - [GET /products] 모든 상품을 가져옴
    @GetMapping("")
    public List<ProductDTO> getProductList() {
        List<ProductDTO> productDTOList = productService.getProductList();
        return productDTOList;
    }

    // 최종 상품 개별데이터 됨 - [GET /products/{id}] 개별 상품 가져옴
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductList(@PathVariable Integer id) {
        ProductDTO productDTO = productService.productList(id);
        if(productDTO == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(productDTO);
        }
    }

    // 상품이미지테이블 뽑기

//    @GetMapping("/product-img/{productId}")
//    public List<ProductImgDTO> getProductImagesByProductId(@PathVariable Integer productId) {
//        List<ProductImgEntity> productImgEntities = productImgRepository.findByProductEntityProductId(productId);
//        List<ProductImgDTO> productImgDTOs = productImgEntities.stream()
//                .map(ProductImgDTO::toProductImgDTO)
//                .collect(Collectors.toList());
//        return productImgDTOs;
//    }

    @GetMapping("/product-img/{productId}")
    public ResponseEntity<List<ProductImgDTO>> getProductImagesByProductId(@PathVariable Integer productId) {
        List<ProductImgDTO> productImgDTOs = productImgService.getProductImagesByProductId(productId);
        return ResponseEntity.ok(productImgDTOs);
    }

    // 최종 상품 삭제 됨- [DELETE /products/{id}] 상품 삭제
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable("id") Integer productId) {
        productService.deleteProduct(productId);
    }

}


