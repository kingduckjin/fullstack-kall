package com.project.kall.service;

import com.project.kall.dto.CartDTO;
import com.project.kall.dto.OrderDetailDTO;
import com.project.kall.entity.CartEntity;
import com.project.kall.entity.OrderDetailEntity;
import com.project.kall.entity.OrderEntity;
import com.project.kall.entity.ProductEntity;
import com.project.kall.repository.CartRepository;
import com.project.kall.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    @Autowired
    CartRepository cartRepository;
    @Autowired
    OrderRepository orderRepository;

//  -----------------------------------userId 별 카트 목록 --------------------------------------------------------------
    public List<CartDTO> cartList(String userId) {
        List<CartEntity> cartEntityList = cartRepository.findByUserEntityUserId(userId);
        List<CartDTO> cartDTOList = new ArrayList<>();
        for(CartEntity cartEntity : cartEntityList) {
            if(cartEntity != null) {
                cartDTOList.add(CartDTO.toCartDTO(cartEntity));
            }
        }
        return cartDTOList;
    }

//  -----------------------------------userId로 찾아서 cart 삭제 (customer) ---------------------------------------------
    public void deleteCart( OrderDetailDTO orderDetailDTO, String id) {
        List<CartDTO> cartList = cartList(id); // user_id로 찾음 carlist를...
        Integer productId = orderDetailDTO.getProductDTO().getProductId();
        for(CartDTO cartDTO : cartList) {
            if(cartDTO.getProductDTO().getProductId() == productId) {
                Integer cartId = cartDTO.getCartId();
                cartRepository.deleteById(cartId);
            }
        }
    }

    //  ----------------------------------cart 추가 (customer) ----------------------------------------------------------
    public void cartAdd(CartDTO cartDTO) {
        if(cartDTO != null) {
            CartEntity cartEntity = CartEntity.toCartEntity(cartDTO);
            cartRepository.save(cartEntity);
        }
    }
}
