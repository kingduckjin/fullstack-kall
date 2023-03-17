package com.project.kall.service;

import com.project.kall.dto.ProductQDTO;
import com.project.kall.entity.ProductQEntity;
import com.project.kall.repository.ProductQRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserProductQService {
    private final ProductQRepository productQRepository;

    //━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ 문의디테일 ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    public ProductQDTO findById(Integer productQId) {
        Optional<ProductQEntity> productQEntity = productQRepository.findById(productQId);
        ProductQEntity Question = new ProductQEntity();
        if (productQEntity.isPresent()) {
            Question = productQEntity.get();
        }
        ProductQDTO productQDTO = ProductQDTO.toProductQDTO(Question);
        return productQDTO;
    }

    //━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ 해당유저의 문의목록 ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    public List<ProductQDTO> findAll(String userId) {
        List<ProductQEntity> productQEntities = productQRepository.findAllByOrderByProductQIdDesc();
        List<ProductQDTO> productQDTOList = new ArrayList<>();
        for (ProductQEntity question : productQEntities) {
            if (userId.equals(question.getUserEntity().getUserId())) {
                productQDTOList.add(ProductQDTO.toProductQDTO(question));
            }
        }
        return productQDTOList;
    }

    //━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ 해당 상품문의 수정 ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    public Integer update(ProductQDTO productQDTO) {
        ProductQEntity productQEntity = ProductQEntity.toProductQEntity(productQDTO);
        productQEntity.setDate1(LocalDateTime.now());
        productQRepository.save(productQEntity);
        return 1;
    }

    //━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ 해당 상품문의 삭제 ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    public Integer delete(Integer productQId) {
        productQRepository.deleteById(productQId);
        return 1;
    }

    //━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ 상품문의등록 ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    public Integer save(ProductQDTO productQDTO) {
        ProductQEntity productQEntity = ProductQEntity.toProductQEntity(productQDTO);
        Integer saveQuestion = productQRepository.save(productQEntity).getProductQId();
        return saveQuestion;
    }

    //━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ 해당하는 상품의 문의조회 ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    public List<ProductQDTO> findAllproduct(Integer productId) {
        List<ProductQEntity> productQEntities = productQRepository.findAll();
        System.out.println(productId);
        List<ProductQDTO> productQList = new ArrayList<>();
        for (ProductQEntity productQ : productQEntities) {
            if (productId == productQ.getProductEntity().getProductId()) {
                productQList.add(ProductQDTO.toProductQDTO(productQ));
            }
        }
        return productQList;
    }

    public List<ProductQDTO> findAllforAll() {
        List<ProductQEntity> productQEntities = productQRepository.findAll();
        List<ProductQDTO> productQDTOList = new ArrayList<>();
        for (ProductQEntity productQ : productQEntities){
            productQDTOList.add(ProductQDTO.toProductQDTO(productQ));
        }
        return productQDTOList;
    }
}
