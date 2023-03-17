package com.project.kall.controller;

import com.project.kall.dto.ProductQDTO;
import com.project.kall.dto.UsQDTO;
import com.project.kall.service.UserUsQService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/directs")
public class UserUsQController {
    private final UserUsQService userUsQService;

    @GetMapping("/user/{user_id}")
    public List<UsQDTO> userFindAll(@PathVariable("user_id") String userId) {
        return userUsQService.userfindAll(userId);
    }

    @GetMapping("")
    public List<UsQDTO> findAllforAll(){
        return userUsQService.findAllforAll();
    }
    @GetMapping("/{id}")
    public UsQDTO userFindById(@PathVariable("id") Integer usQId) {
        return userUsQService.userfindById(usQId);
    }

    @PutMapping("/{id}")
    public String userUpdate(@PathVariable("id") Integer usQId, @RequestBody UsQDTO usQDTO) {
        if (userUsQService.userUpdate(usQDTO) > 0) {
            return "ok";
        } else {
            return "failed";
        }
    }

    @DeleteMapping("/{id}")
    public String userDelete(@PathVariable("id") Integer usQId) {
        if (userUsQService.userDelete(usQId) > 0) {
            return "ok";
        } else {
            return "failed";
        }
    }

    @PostMapping("")
    public String save(@RequestBody UsQDTO usQDTO) {
        if (userUsQService.userSave(usQDTO) > 0) {
            return "ok";
        } else {
            return "failed";
        }
    }
}
