package com.project.kall.service;

import aj.org.objectweb.asm.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.kall.dto.ProductDTO;
import com.project.kall.dto.ProductQDTO;
import com.project.kall.entity.ProductEntity;
import com.project.kall.entity.ProductQEntity;
import com.project.kall.repository.ProductQRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductQService {

    @Autowired
    ProductQRepository productQRepository;

    //    ----------------------------모든 문의 목록 -------------------------------------------------------------------------
    public List<ProductQDTO> findAll() {
        List<ProductQEntity> productQEntityList = productQRepository.findAllByOrderByProductQIdDesc();
        List<ProductQDTO> productQDTOList = new ArrayList<>();
        for(ProductQEntity productQEntity : productQEntityList) {
            if(productQEntity != null) {
                productQDTOList.add(ProductQDTO.toProductQDTO(productQEntity));
            }
        }
        return productQDTOList;
    }

    //    ----------------------------개별 문의 목록 -------------------------------------------------------------------------
    public ProductQDTO question(Integer id) {
        Optional<ProductQEntity> optionalproductQEntity = productQRepository.findById(id);
        if(optionalproductQEntity.isPresent()) {
            ProductQEntity productQEntity = optionalproductQEntity.get();
            ProductQDTO productQDTO = ProductQDTO.toProductQDTO(productQEntity);
            return productQDTO;
        } else return null;
    }

    //    ----------------------------개별 문의 답변 -------------------------------------------------------------------------
    public ProductQDTO qcomment(Integer id, ProductQDTO productQDTO) {
        if(productQDTO != null) {
            ProductQEntity productQEntity = ProductQEntity.toProductQEntity(productQDTO);
            productQRepository.save(productQEntity);
        }
        return question(id);
    }

    //  ---------------------------- 삭제 --------------------------------------------------------------------------------
//  -----개별 삭제 --------------------------------------------
    public void delete(Integer id) {
        productQRepository.deleteById(id);
    }


    public void deletes(Integer[] list) {
        List<Integer> idList = new ArrayList<>(Arrays.asList(list));
        for(Integer id : idList) {
            if(id != null || id >= 0) {
                System.out.println(id);
                delete(id);
            }
        }
    }




}