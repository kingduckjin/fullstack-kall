package com.project.kall.controller;

import com.project.kall.dto.UsAddressDTO;
import com.project.kall.entity.UsAddressEntity;
import com.project.kall.service.UsAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/addresses")
@RequiredArgsConstructor
@CrossOrigin("*")
public class UsAddressController {

    private final UsAddressService usAddressService;

    @GetMapping("/{id}") //선택한 주소를 가져옴
    public UsAddressDTO findById(@PathVariable("id") Integer addressId) {
        System.out.println("★ Controller ★");
        return usAddressService.findById(addressId);
    }

    @GetMapping("/user/{user_id}") //각 유저가 작성한 주소를 가져옴
    public List<UsAddressDTO> findAll(@PathVariable("user_id") String userId) {
        System.out.println("★ Controller ★ userId = " + userId);
        return usAddressService.findAll(userId);
    }

    @PostMapping("") //유저가 작성한 주소를 추가
    public String save(@RequestBody UsAddressDTO usAddressDTO) {
        System.out.println("★ Controller ★ usAddressDTO = " + usAddressDTO);
        if (usAddressService.save(usAddressDTO) > 0) {
            return "ok";
        } else {
            return "failed";
        }
    }

    @PutMapping("/{id}") // 해당 id 의 address 업데이트
    public String update(@PathVariable("id") Integer addressId, @RequestBody UsAddressDTO usAddressDTO) {
        if (usAddressService.update(usAddressDTO) > 0) {
            return "ok";
        } else {
            return "failed";
        }
    }

    @PutMapping("/{id}/default") // 기본배송지 설정
    public String defaultUpdate(@PathVariable("id") Integer addressId) {
        if (usAddressService.defaultupdate(addressId) > 0) {
            return "ok";
        } else {
            return "failed";
        }
    }

    @DeleteMapping("/{id}") //삭 완
    public String delete(@PathVariable("id") Integer id) {
        System.out.println("addressId = " + id);
        if (usAddressService.delete(id) > 0) {
            return "ok";
        } else {
            return "failed";
        }
    }


}