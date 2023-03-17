package com.project.kall.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.kall.dto.ProductDTO;
import com.project.kall.dto.ProductQDTO;
import com.project.kall.repository.ProductQRepository;
import com.project.kall.service.ProductQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin/questions")
public class ProductQController {

    @Autowired
    ProductQService productQService;

//    ----------------------------모든 문의 목록 -------------------------------------------------------------------------
    @GetMapping("")
    public List<ProductQDTO> productQDTOList() {
        return productQService.findAll();
    }

//    ----------------------------개별 문의 목록 -------------------------------------------------------------------------
    @GetMapping("/{id}") // id = productQId
    public ProductQDTO question(@PathVariable Integer id) {
        return productQService.question(id);
    }

//  ----------------------------개별 문의 답변 등록 / 내용 수정 다해당 -------------------------------------------------------------------------
    @PutMapping("/{id}") //id = productQId
    public ProductQDTO qcomment(@PathVariable Integer id, @RequestBody ProductQDTO productQDTO) {
        return productQService.qcomment(id, productQDTO);
    }

//    ----------------------------개별 문의 삭제-------------------------------------------------------------------
    @DeleteMapping("/{id}") //id = productQId
    public List<ProductQDTO> delete(@PathVariable Integer id) {
        productQService.delete(id);
        return productQService.findAll();
    }

    @DeleteMapping("") // 다중 삭제 배열 productQId
    public List<ProductQDTO> deletes(@RequestBody Integer[] list) {
        productQService.deletes(list);
        return productQService.findAll();
    }

}
