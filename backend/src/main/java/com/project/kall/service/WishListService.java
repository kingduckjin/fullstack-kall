package com.project.kall.service;

import com.project.kall.dto.ProductDTO;
import com.project.kall.dto.WishListDTO;
import com.project.kall.entity.ProductEntity;
import com.project.kall.entity.WishListEntity;
import com.project.kall.repository.ProductRepository;
import com.project.kall.repository.WishListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WishListService {

    @Autowired
    WishListRepository wishListRepository;
    @Autowired
    ProductRepository productRepository;


//  -----위시 리스트 목록 ------------------------------------------------------------------------------------------------
    public List<WishListDTO> findAllById(String id) {
        List<WishListEntity> wishListEntityList = wishListRepository.findUserId(id);
        List<WishListDTO> wishListDTOList = new ArrayList<>();
        for(WishListEntity wishListEntity: wishListEntityList) {
            if(wishListEntity != null) {
                wishListDTOList.add(WishListDTO.toWishListDTO(wishListEntity));
            }
        }
        return wishListDTOList;
    }

//  -----위시 리스트 추가 ------------------------------------------------------------------------------------------------
    public List<WishListDTO> addWishList(WishListDTO wishListDTO) {
        if(wishListDTO != null) {
            WishListEntity wishListEntity = WishListEntity.toWishListEntity(wishListDTO);
            wishListRepository.save(wishListEntity);
        }
        return findAllById(wishListDTO.getUserDTO().getUserId());
    }

//  -----위시 리스트 삭제 ------------------------------------------------------------------------------------------------
    public List<WishListDTO> delete(Integer id) {
        Optional<WishListEntity> optionalwishListEntity = wishListRepository.findById(id);
        if(optionalwishListEntity.isPresent()) {
            WishListEntity wishListEntity = optionalwishListEntity.get();
            wishListRepository.deleteById(id);
            return findAllById(wishListEntity.getUserEntity().getUserId());
        } else return null;
    }

    public ProductDTO prodouctInfo(Integer id) {
        Optional<ProductEntity> optionalProductEntity = productRepository.findById(id);
        if(optionalProductEntity.isPresent()) {
            ProductEntity productEntity = optionalProductEntity.get();
            ProductDTO productDTO = ProductDTO.toProductDTO(productEntity);
            return productDTO;
        } else return null;
    }
}
