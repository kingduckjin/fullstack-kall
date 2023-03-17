package com.project.kall.controller;

import com.project.kall.dto.CategoryDTO;
import com.project.kall.dto.ProductQDTO;
import com.project.kall.dto.UsQDTO;
import com.project.kall.dto.UserDTO;
import com.project.kall.service.UsQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/admin/directs")
public class UsQController {

    @Autowired
    UsQService usQService;

//    -----------------------------------문의글 리스트--------------------------------------------------------
    @GetMapping("")
    public  List<UsQDTO> list() {return usQService.findAll();}

//    ----------------------------문의글 detail _ 개별 문의 가져옴 ---------------------------------------------------------
    @GetMapping("/{id}")  // id = usqID
    public UsQDTO findById(@PathVariable Integer id) {
        return usQService.findById(id);
    }

//    ----------------------------------문의글 삭제 --------------------------------------------------------

    @DeleteMapping("/{id}") // id = usqID -> 단일
    public List<UsQDTO>  delete(@PathVariable Integer id) {
       usQService.delete(id);
       return list();
    }
    @Transactional
    @DeleteMapping("") // => 일괄삭제
    public List<UsQDTO> deletes(@RequestBody String[] list) {
        usQService.deletes(list);
        return list();
    }

//    ---------------------답글 수정 _ 문의제목, 내용, 카테고리 수정 -------------------------------------------------------
    @PutMapping("/{id}") // id = usqID
    public UsQDTO update(@PathVariable Integer id, @RequestBody UsQDTO usQDTO) throws IOException {
        usQService.update(usQDTO);
        return usQService.findById(id);
    }
    // 수정 완료 후 그 글의 datail 값 확인하게


//    ----------------------------- 해당 유저의 모든 문의 가져오기 ----------------------------------------------------------
    @GetMapping("/user/{id}") // userid
    public List<UsQDTO> useridlist(@PathVariable("id") String userId) {
        return usQService.finduserQ(userId);
    }

//    -----------------------------------카테고리 가져오기 ----------------------------------------------------------------
    @GetMapping("/categories/{id}") // usqId
    public CategoryDTO categoryDTO(@PathVariable("id") Integer id) {
        return usQService.getCategory(id);
    }

//    -----------------------------------카테고리 가져오기 ----------------------------------------------------------------
    @GetMapping("/users/{id}") // usqId
    public UserDTO userDTO(@PathVariable("id") Integer id) {
        return usQService.getUser(id);
    }


//    -------------------------------개별 문의 덧글 상태 구정하기 ----------------------------------------------------------------
    @PatchMapping("/{id}") // id = usqID
    public UsQDTO updateComment(@PathVariable Integer id, @RequestBody UsQDTO usQDTO) throws IOException {
        usQService.update(usQDTO);
        return usQService.findById(id);
    }

}

