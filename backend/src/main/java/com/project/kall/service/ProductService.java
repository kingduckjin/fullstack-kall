package com.project.kall.service;

import com.project.kall.dto.ProductDTO;
import com.project.kall.dto.ProductImgDTO;
import com.project.kall.entity.CategoryEntity;
import com.project.kall.entity.ProductEntity;
import com.project.kall.entity.ProductImgEntity;
import com.project.kall.repository.CategoryRepository;
import com.project.kall.repository.ProductImgRepository;
import com.project.kall.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;
import javax.persistence.EntityNotFoundException;

import javax.persistence.EntityManager;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



@Service
@RequiredArgsConstructor
public class ProductService {

    @Autowired
    private  ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private  ProductImgRepository productImgRepository;

    @Autowired
    private EntityManager entityManager;


    private ProductDTO productDTO;



    // 상품등록
    public ProductDTO registerProduct(ProductDTO productDTO, MultipartFile file) {
        String uploadDir = "C:/Users/ad/Desktop/front/src/images/upload"; // 파일 업로드 경로 설정
        String originalFilename = file.getOriginalFilename();
        String[] fileParts = originalFilename.split("\\.");
        String extension = fileParts[fileParts.length - 1];
        String filename = productDTO.getName() + "_" + originalFilename;
        String fileUrl = "/src/images/upload/" + filename; // 이미지 파일의 URL
        Path targetLocation = Paths.get(uploadDir + "/" + filename); // 파일 경로

        // ProductDTO를 ProductEntity로 변환합니다.
        ProductEntity productEntity = ProductEntity.toSetProductEntity(ProductDTO.toProductDTO(ProductEntity.toSetProductEntity(productDTO)));

        // 이미지 파일 경로 정보를 ProductEntity에 추가합니다.
        productEntity.setImage(fileUrl);

        //상품 엔티티를 데이터베이스에 저장합니다
        productEntity = productRepository.save(productEntity);

        System.out.println(productEntity);
        // 파일을 업로드합니다.
        try {
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            throw new ProductImgService.FileStorageException("Failed to store file " + filename, ex);
        }

        // ProductEntity를 ProductDTO로 다시 변환하고, 해당 객체를 반환합니다.
        return ProductDTO.toProductDTO(productEntity);
    }

    // 상품 메인 사진 수정

    //public ProductDTO updateProductImage(Integer productId, MultipartFile file) {
    public ProductDTO updateProductImage(Integer productId, MultipartFile file) {

        // productId에 해당하는 상품 엔티티를 조회합니다.
        ProductEntity productEntity = productRepository.findById(productId)
                .orElseThrow(EntityNotFoundException::new);

        // 파일 업로드 경로 설정
        String uploadDir = "C:/Users/ad/Desktop/front/src/images/upload"; // 파일 업로드 경로 설정





        // 파일 이름을 productId와 파일 확장자로 결정합니다.
        String originalFilename = file.getOriginalFilename();
        String[] fileParts = originalFilename.split("\\.");
        String extension = fileParts[fileParts.length - 1];
        String filename = productEntity.getName() + "_" + originalFilename;
        String fileUrl = "/src/images/upload/" + filename; // 이미지 파일의 URL

        try {
            // 파일 이름에 '..'가 포함되어 있으면 예외를 던집니다.
            if (originalFilename.contains("..")) {
                throw new ProductImgService.FileStorageException("Invalid filename");
            }

            // 파일을 업로드할 경로를 설정합니다.
            Path targetLocation = Paths.get(uploadDir + "/" + filename); // 파일 경로
            // 파일을 복사합니다.
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            throw new ProductImgService.FileStorageException("Failed to store file " + filename, ex);
        }

        // 이전 이미지 파일을 삭제합니다.
        String oldFilePath = productEntity.getImage();
        File oldFile = new File(oldFilePath);
        if (oldFile.exists()) {
            oldFile.delete();
        }

        // 상품 엔티티의 이미지 경로를 업데이트합니다.
        productEntity.setImage(fileUrl);
        productRepository.save(productEntity);

        // ProductEntity를 ProductDTO로 다시 변환하고, 해당 객체를 반환합니다.
        return ProductDTO.toProductDTO(productEntity);
    }


    //상품 나머지 수정
    public ProductDTO updateProductExceptImage(Integer productId, ProductDTO productDTO) {
        Optional<ProductEntity> productEntity = productRepository.findById(productId);

        if (productEntity.isPresent()) {
            ProductEntity entity = productEntity.get();

            // Update only non-null fields in the DTO
            if (productDTO.getCategoryDTO() != null) {
                entity.setCategoryEntity(CategoryEntity.toCategoryEntity(productDTO.getCategoryDTO()));
            }
            if (productDTO.getName() != null) {
                entity.setName(productDTO.getName());
            }
            if (productDTO.getPrice() != null) {
                entity.setPrice(productDTO.getPrice());
            }
            if (productDTO.getAmount() != null) {
                entity.setAmount(productDTO.getAmount());
            }
            if (productDTO.getInfo() != null) {
                entity.setInfo(productDTO.getInfo());
            }
            if (productDTO.getDate() != null) {
                entity.setDate(productDTO.getDate());
            }
            if (productDTO.getStar() != null) {
                entity.setStar(productDTO.getStar());
            }
            if (productDTO.getTag() != null) {
                entity.setTag(productDTO.getTag());
            }

            // Save the updated entity to the database
            ProductEntity updatedProductEntity = productRepository.save(entity);

            // Convert the updated entity to DTO and return it
            ProductDTO updatedProductDTO = ProductDTO.toProductDTO(updatedProductEntity);
            return updatedProductDTO;
        } else {
            throw new RuntimeException("상품을 찾을 수 없습니다.");
        }
    }

    // 메인에 최신 6개 상품 보내기
    public List<ProductDTO> recentProducts() {
        List<ProductEntity> recentProducts = productRepository.findTop6ByOrderByProductIdDesc();
        List<ProductDTO> productDTOS = new ArrayList<>();
        for(ProductEntity productEntity : recentProducts) {
            ProductDTO productDTO = ProductDTO.toProductDTO(productEntity);
            productDTOS.add(productDTO);
        }
        return productDTOS;
    }

    // 상품 검색
    public List<ProductDTO> searchProducts(String keyword) {
        List<ProductEntity> productEntityList = new ArrayList<>();
        productEntityList = productRepository.findByNameContainingIgnoreCaseOrTagContainingIgnoreCase(keyword,keyword);

        List<ProductDTO> productList = new ArrayList<>();
        for (ProductEntity productEntity : productEntityList) {
            productList.add(ProductDTO.toProductDTO(productEntity));
        }
        return productList;
    }

    //상품 카테고리 구분없이  목록 조회
    public List<ProductDTO> getProductList() {
        List<ProductEntity> productEntityList = productRepository.findAll();
        List<ProductDTO> productDTOList = new ArrayList<>();

        for (ProductEntity productEntity : productEntityList) {
            ProductDTO productDTO = ProductDTO.toProductDTO(productEntity);
            productDTOList.add(productDTO);
        }

        return productDTOList;
    }



    // 상품 카테고리별 목록 조회

    //  ---- 주문제작 리스트 -------------------------------------------------------------------------------------------------
    public List<ProductDTO> customerProduct() {
        String id = "custom";
        List<ProductEntity> productEntityList = productRepository.findCategoryName(id);
        List<ProductDTO> productDTOList = new ArrayList<>();

        for(ProductEntity productEntity : productEntityList) {
            System.out.println("여기야" + productEntity);
            if(productEntity != null) {
                productDTOList.add(ProductDTO.toProductDTO(productEntity));
            }
        }
        return  productDTOList;
    }

//  ---- 디자인 리스트 -------------------------------------------------------------------------------------------------
    public List<ProductDTO> designProduct() {
        String id = "design";
        List<ProductEntity> productEntityList = productRepository.findCategoryName(id);
        List<ProductDTO> productDTOList = new ArrayList<>();

        for(ProductEntity productEntity : productEntityList) {
            if(productEntity != null) {
                productDTOList.add(ProductDTO.toProductDTO(productEntity));
            }
        }
        return  productDTOList;
    }

//  ---- 기타 리스트 -------------------------------------------------------------------------------------------------
    public List<ProductDTO> etcProduct() {
        String id = "etc";
        List<ProductEntity> productEntityList = productRepository.findCategoryName(id);
        List<ProductDTO> productDTOList = new ArrayList<>();

        for(ProductEntity productEntity : productEntityList) {
            if(productEntity != null) {
                productDTOList.add(ProductDTO.toProductDTO(productEntity));
            }
        }
        return  productDTOList;
    }

    // 개별 상품 부르기
    public ProductDTO productList(Integer id) {
        Optional<ProductEntity> optionalproductEntity = productRepository.findById(id);
        if(optionalproductEntity.isPresent()) {
            ProductEntity productEntity = optionalproductEntity.get();
            productEntity.setCnt(productEntity.getCnt()+1);
            System.out.println("product get" + (productEntity.getCnt()+1));
            System.out.println("productEntity"+productEntity.getCnt());
            productRepository.save(productEntity);
            ProductDTO productDTO = ProductDTO.toProductDTO(productEntity);
            return productDTO;
        } else return null;
    }



////  ---- 카테고리별 5점 추천 리스트 ---------------------------------------------------------------------------------------------------
//    public List<ProductDTO> recommentList(String id) { // id = 카테고리
//        List<ProductEntity> productDTOEntity = productRepository.findStar(id); // 카테고리 & 별 5인 select
//        List<ProductDTO> productDTOList = new ArrayList<>();
//        for(ProductEntity productEntity : productDTOEntity) {
//            if(productEntity != null) {
//                productDTOList.add(ProductDTO.toProductDTO(productEntity));
//            }
//        }
//        return productDTOList;
//    }


    // 상품 삭제
    public void deleteProduct(Integer productId) {
        Optional<ProductEntity> productEntity = productRepository.findById(productId);

        if (productEntity.isPresent()) {
            // 상품을 삭제합니다.
            productRepository.deleteById(productId);
        } else {
            // 상품이 없을 경우 예외 처리
            throw new RuntimeException("상품을 찾을 수 없습니다." );
        }
    }

//    // 다수의 상품 삭제
//    public void deletes(Integer[] list) {
//        List<Integer> idList = new ArrayList<>(Arrays.asList(list));
//        for(Integer id : idList) {
//            if(id != null || id >= 0) {
//                System.out.println(id);
//                delete(id);
//            }
//        }
//    }
//
//    public List<ProductDTO> findAll() {
//        List<ProductEntity> productEntityList = productRepository.findAll();
//        List<ProductDTO> productDTOList = new ArrayList<>();
//        for(ProductEntity productEntity : productEntityList) {
//            if(productEntity != null) {
//                productDTOList.add(ProductDTO.toProductDTO(productEntity));
//            }
//        }
//        return productDTOList;
//    }

//    @Transactional
//    public static void deleteProducts(List<Integer> productIds) {
//        for (Integer productId : productIds) {
//            ProductEntity productEntity = productRepository.findById(productId)
//                    .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));
//            productRepository.delete(productEntity);
//        }
//    }



}
