package com.project.kall.controller;

import com.project.kall.dto.ProductDTO;
import com.project.kall.dto.WishListDTO;
import com.project.kall.service.WishListService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/wishlists")
public class WishlistController {

    @Autowired
    WishListService wishListService;

//  -----위시 리스트 목록 _ 특정 유저의 찜목록가져오기--------------------------------------------------------------------------
    @GetMapping("/user/{id}") // id = user_id
    public List<WishListDTO> wishListDTOList(@PathVariable String id) { // id = user_id
        return wishListService.findAllById(id);
    }

//  -----위시 리스트 추가 _ 찜목록에 새로운 항목 추가 -------------------------------------------------------------------------
    @PostMapping("") // wishlistDTO 필요
    public  List<WishListDTO> addWishList(@RequestBody WishListDTO wishListDTO) {
        return wishListService.addWishList(wishListDTO);
    }

//  -----위시 리스트 삭제 _ 특정 찜목록 제거 ---------------------------------------------------------------------------------
    @DeleteMapping("/{id}") // => id = wishlistId
    public List<WishListDTO> addWishList(@PathVariable Integer id ) { // id = review_id
        return wishListService.delete(id);
    }

//  -----위시 리스트 상품 정보 가져오기 _ 찜목록 중에서 특정 상품 가져오기 --------------------------------------------------------
    @GetMapping("/product/{id}") // -> id=productId
    public ProductDTO productInfo(@PathVariable Integer id) {
        return wishListService.prodouctInfo(id);
    }

}
