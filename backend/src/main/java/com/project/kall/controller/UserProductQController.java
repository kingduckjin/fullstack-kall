package com.project.kall.controller;

import com.project.kall.dto.ProductQDTO;
import com.project.kall.service.ProductQService;
import com.project.kall.service.UserProductQService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/questions")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserProductQController {
    private final UserProductQService userProductQService;

    //━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ 문의디테일 ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    @GetMapping("/{id}")
    public ProductQDTO findById(@PathVariable("id") Integer productQId) {
        System.out.println("★ Controller 문의 디테일★");
        return userProductQService.findById(productQId);
    }

    //━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ 해당유저의 문의목록 ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    @GetMapping("/user/{user_id}")
    public List<ProductQDTO> findAll(@PathVariable("user_id") String userId) {
        System.out.printf("★ Controller 유저 문의목록 ★");
        return userProductQService.findAll(userId);
    }

    @GetMapping("")
    public List<ProductQDTO> findAllforAll(){
        return userProductQService.findAllforAll();
    }


    //━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ 해당 상품문의 수정 ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    @PutMapping("/{id}")
    public String update(@PathVariable("id") Integer productQId, @RequestBody ProductQDTO productQDTO) {
        System.out.println("★ Controller ★ 해당 문의 수정");
        if (userProductQService.update(productQDTO) > 0) {
            return "ok";
        } else {
            return "failed";
        }
    }

    //━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ 해당 상품문의 삭제 ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Integer productQId) {
        System.out.println("★ Controller ★ 해당문의삭제");
        if (userProductQService.delete(productQId) > 0) {
            return "ok";
        } else {
            return "failed";
        }
    }

    //━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ 상품문의등록 ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    @PostMapping("")
    public String save(@RequestBody ProductQDTO productQDTO) {
        System.out.println("★ Controller ★ 상품문의추가");
        if (userProductQService.save(productQDTO) > 0) {
            return "ok";
        } else {
            return "failed";
        }
    }
    //━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ 해당하는 상품의 문의목록 ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    @GetMapping("/product/{id}")
    public List<ProductQDTO> findAllproduct(@PathVariable("id") Integer productId ){

        return userProductQService.findAllproduct(productId);
    }

}
