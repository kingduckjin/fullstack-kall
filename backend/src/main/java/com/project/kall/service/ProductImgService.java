package com.project.kall.service;

import com.project.kall.dto.ProductDTO;
import com.project.kall.dto.ProductImgDTO;
import com.project.kall.entity.ProductEntity;
import com.project.kall.entity.ProductImgEntity;
import com.project.kall.repository.ProductImgRepository;
import com.project.kall.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


import javax.persistence.EntityNotFoundException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductImgService {
    @Autowired
    private ProductImgRepository productImgRepository;

    @Autowired
    private ProductRepository productRepository;


//    public ProductImgDTO saveProductImg(ProductImgDTO productImgDTO) {
//        ProductImgEntity productImgEntity = ProductImgEntity.toSetProductImgEntity(productImgDTO);
//        productImgEntity = productImgRepository.save(productImgEntity);
//        return ProductImgDTO.toProductImgDTO(productImgEntity);
//    }

    // 이미지 등록
    public List<ProductImgDTO> saveProductImages(Integer productId, List<MultipartFile> files) {
        // productId에 해당하는 상품 엔티티를 조회합니다.
        System.out.println(productId);
        ProductEntity productEntity = productRepository.findById(productId)
                .orElseThrow(EntityNotFoundException::new);
        System.out.println(productId);

        List<ProductImgDTO> productImgDTOList = new ArrayList<>();
        String uploadDir = "C:/Users/ad/Desktop/front/src/images/upload"; // 파일 업로드 경로 설정
        File dir = new File(uploadDir);

        if (!dir.exists()) {
            boolean created = dir.mkdirs(); // 파일 업로드 경로가 없으면 생성
            if (!created) {
                throw new RuntimeException("Failed to create upload directory");
            }
        }

        for (MultipartFile file : files) {
            ProductImgDTO productImgDTO = new ProductImgDTO();
            // 파일 업로드 처리
            String originalFilename = file.getOriginalFilename();
            String[] fileParts = originalFilename.split("\\.");
            String extension = fileParts[fileParts.length - 1];
            String filename = productEntity.getName() + "_" + originalFilename;
            String fileUrl = "/src/images/upload/" + filename; // 이미지 파일의 URL

            try {
                if (originalFilename.contains("..")) {
                    throw new FileStorageException("Invalid filename");
                }
                Path targetLocation = Paths.get(uploadDir + "/" + filename);
                Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ex) {
                throw new FileStorageException("Failed to store file " + filename, ex);
            }

//                   // 기존 파일 삭제
//            String oldFilePath = productImgEntity.getImgUrl();
//            File oldFile = new File(oldFilePath);
//            if (oldFile.exists()) {
//                oldFile.delete();
//            }

            // ProductImgDTO에 필요한 정보 설정
            productImgDTO.setProductDTO(ProductDTO.toProductDTO(productRepository.findById(productId).orElseThrow(EntityNotFoundException::new)));
            productImgDTO.setOriImgName(originalFilename);
            productImgDTO.setImgName(filename);
            productImgDTO.setImgUrl(fileUrl);
            // ProductImgDTO를 ProductImgEntity로 변환 후 저장
            ProductImgEntity productImgEntity = ProductImgEntity.toSetProductImgEntity(productImgDTO);
            productImgRepository.save(productImgEntity);
            // 저장된 ProductImgEntity를 다시 ProductImgDTO로 변환 후 List에 추가
            productImgDTOList.add(ProductImgDTO.toProductImgDTO(productImgEntity));
        }

        return productImgDTOList;
    }


    // FileStorageException 클래스 정의
    public static class FileStorageException extends RuntimeException {

        public FileStorageException(String message) {
            super(message);
        }

        public FileStorageException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    //리스트 뽑기
    public List<ProductImgDTO> getProductImagesByProductId(Integer productId) {
        List<ProductImgEntity> productImgEntities = productImgRepository.findByProductEntityProductId(productId);
        return productImgEntities.stream()
                .map(ProductImgDTO::toProductImgDTO)
                .collect(Collectors.toList());
    }
}