package com.project.kall.controller;


import com.project.kall.dto.ProductDTO;
import com.project.kall.dto.UserDTO;
import com.project.kall.entity.UserDelEntity;
import com.project.kall.entity.UserEntity;
import com.project.kall.repository.UserRepository;
import com.project.kall.service.ProductService;
import com.project.kall.service.UserDelService;
import com.project.kall.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final UserDelService userDelService;

    private UserRepository userRepository;
    private final ProductService productService;


    // 최종 회원목록 출력 됨..- [GET /users] 모든 유저를 가져옴
    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> findAll(){
        List<UserDTO> userDTOList = userService.findAll();
        return ResponseEntity.ok(userDTOList);
    }

    // 최종 회원 개별 데이터 됨..
    @GetMapping("/users/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable("id") String userId){
        UserDTO userDTO = userService.findById(userId);
        if(userDTO == null){
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(userDTO);
        }
    }

    // 최종 회원탈퇴( 활동상태 변경) 됨.. - [DELETE /users/{id}, {탈퇴 데이터}] 관리자에 의한 강제 탈퇴 구현에 필요 이 요청이 가는 순간 us_del 테이블에 쌓이게 되고 실제로 유저리스트에서 사라지는 것은 아님. 유저 리스트에는 여전히 남아있지만 해당 유저의 레코드에서 status를 '탈퇴'로 변경함.
    @DeleteMapping ("/users/{id}")
    public String delete(@PathVariable("id") String userId){
        UserEntity userEntity = userService.loadUserByUserId(userId);
        userEntity.setStatus("탈퇴회원");
        userRepository.save(userEntity);

        UserDelEntity userDelEntity = new UserDelEntity();
        userDelEntity.setUserEntity(userEntity);
        userDelEntity.setUserId(userId);
        userDelEntity.setText("관리자에 의한 삭제");

        userDelService.saveDeletedUser(userDelEntity);
        return "User with userId " + userId + " has been deleted.";
    }


    // 최종 유저 수정- [PUT /admin/users/{id}, {변경 데이터}] 개별 유저를 수정함. 마이페이지와는 다르게 모든 값을 수정할 수 있음.
    @PutMapping("/users/{userId}")
    public UserDTO updateUser(@PathVariable String userId, @RequestBody UserDTO userDTO) {
        return userService.updateUser(userId, userDTO);
    }

    //최종 다수의 유저 삭제 [DELETE /admin/users, {유저 아이디 배열}] 유저 아이디가 담긴 배열을 이용해 다수의 유저를 삭제

    @DeleteMapping("/users")
    public void deleteUsers(@RequestBody String[] userIds) {
            userDelService.deleteI(userIds);
    }

    // 다수의 상품 삭제  - [DELETE /admin/products, {삭제 상품 아이디 배열}] 상품 여러개 삭제
//
//    @DeleteMapping("")
//    public List<ProductDTO> deletes(@RequestBody Integer[] list) {
//        productService.deletes(list);
//        return productService.findAll();
//    }
//    @DeleteMapping("/products")
//    public void deleteProducts(@RequestBody List<Integer> productIds) {
//        ProductService.deleteProducts(productIds);
//    }


}
