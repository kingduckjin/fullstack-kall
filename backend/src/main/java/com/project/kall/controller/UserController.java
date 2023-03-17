package com.project.kall.controller;

import com.project.kall.dto.UserDelDTO;
import com.project.kall.entity.UserDelEntity;
import com.project.kall.entity.UserEntity;
import com.project.kall.repository.UserRepository;
import com.project.kall.dto.UserDTO;
import com.project.kall.service.JwtService;
import com.project.kall.service.UserDelService;
import com.project.kall.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@CrossOrigin("*")
public class UserController {
    private final UserService userService;
    private final UserDelService userDelService;
    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    private UserDelDTO userDelDTO;

    @Autowired
    private UserRepository userRepository;


    // 최종 회원가입 됨! - POST /users, {신규 유저 데이터} - 회원가입
    @PostMapping("")
    public String save(@RequestBody UserDTO userDTO) {
        return userService.save(userDTO);
    }

    // 최종 아이디 중복체크 됨..
    @GetMapping("/idCheck")
    public String idCheck(@PathVariable("id") @RequestParam String userId) {
        if (userService.isDuplicatedId(userId)) {
            return "이미 가입된 회원입니다.";
        }
        return "가입 가능한 아이디입니다.";
    }

    // 최종 이메일 중복체크 됨..
    @GetMapping("/emailCheck")
    public String emailCheck(@PathVariable("id") @RequestParam String email) {
        if (userService.isDuplicatedEmail(email)) {
            return "이미 가입된 이메일입니다.";
        }
        return "가입 가능한 이메일입니다.";
    }

    //최종  로그인됨 - POST /user/login, {아이디, 비밀번호} - 로그인
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody UserDTO userDTO) {
        return userService.login(userDTO);
    }



    // 최종 로그인 이후 인증된 사용자의 정보를 반환하는 API
    @GetMapping("/auth/{userId}")
    public ResponseEntity<?> userInfo(@PathVariable String userId, @RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");
        if (jwtService.validateToken(token)) {
            UserEntity userEntity = userRepository.findById(userId).get();
            userEntity.setPassword(null);
            return new ResponseEntity<UserEntity>(userEntity, HttpStatus.OK);
        }
        return new ResponseEntity<String>("You don't have authorization",HttpStatus.FORBIDDEN);
    }


    // 최종 아이디 찾기
    // 최종- [POST /users/find?type=email, {이름과 이메일}]
    @GetMapping("/findIdByEmail")
    public ResponseEntity<String> findIdByEmail(@RequestParam String name, @RequestParam String email) {
        try {
            String decodedName = URLDecoder.decode(name, "UTF-8");
            String userId = userService.findIdByEmail(decodedName, email);
            return ResponseEntity.ok(userId);
        } catch (UnsupportedEncodingException e) {
            // 인코딩 방식이 지원되지 않을 경우 예외 처리
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 최종- [POST /users/find?type=phone, {이름과 핸드폰 번호}]
    @GetMapping("/findIdByPhone")
    public ResponseEntity<String> findIdByPhone(@RequestParam String name, @RequestParam String phoneNb) {

        try {
            String decodedName = URLDecoder.decode(name, "UTF-8");
            String userId = userService.findIdByPhone(decodedName, phoneNb);
            return ResponseEntity.ok(userId);
        } catch (UnsupportedEncodingException e) {
            // 인코딩 방식이 지원되지 않을 경우 예외 처리
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }



    // 비밀번호찾기

    // GET /users/{userId} - 유저 정보 조회
    // 마이페이지에서도 사용
    @GetMapping("/{userId}")
    public UserDTO getUser(@PathVariable String userId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return UserDTO.toUserDTO(userEntity);
    }

    // PUT /users/{userId}/password - 비밀번호 변경
    @PutMapping("/{userId}/password")
    public String changePassword(@PathVariable String userId) {
        return userService.changePassword(userId);
    }

//    // 비밀번호 찾기 사용인데 태현이가 페이지 수정하면 그때 쓸거
//      // - [POST /users/find?type=email, {이름과 이메일}]
//
//    @GetMapping("/findPasswordByEmail")
//    public ResponseEntity<String> findPasswordByEmail(
//            @RequestParam String UserId,
//            @RequestParam String email) {
//
//        String newPassword = userService.findPasswordByEmail(UserId, email);
//        if (newPassword != null) {
//            return ResponseEntity.ok(newPassword);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//      //- [POST /users/find?type=phone, {이름과 핸드폰 번호}]
//    @GetMapping("/findPasswordByPhone")
//    public ResponseEntity<String> findPasswordByPhone(
//            @RequestParam String UserId,
//            @RequestParam String phoneNb) {
//
//        String newPassword = userService.findPasswordByPhone(UserId, phoneNb);
//        if (newPassword != null) {
//            return ResponseEntity.ok(newPassword);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }



    //최종 마이페이지 수정 비밀번호 확인하기 - [POST /users/passwordcheck, {비밀번호}] 개인정보를 수정하기 전 비밀번호를 입력받을 때 서버로 비밀번호를 전송함. 서버에서는 받은 비밀번호를 데이터베이스에 저장된 유저의 비밀번호와 일치하는지 확인후 true 혹은 false를 반환.
    @PostMapping("/passwordcheck")
    @PreAuthorize("hasRole('USER')")
    public boolean checkPassword(@RequestBody Map<String, String> data) {
        String userId = data.get("userId");
        String password = data.get("password");
        UserEntity user = userService.loadUserByUserId(userId);
        return passwordEncoder.matches(password, user.getPassword());
    }


    // 비밀번호 수정
//    @PutMapping("/password/{id}")
//    public void updatePassword(@PathVariable("id") String userId, @RequestBody UserDTO userDTO) {
//        userService.updatePassword(userId, userDTO.getPassword());
//
//    }

    //최종  비밀번호 수정
    @PutMapping("/password/{id}")
    public ResponseEntity<String> updatePassword(@PathVariable("id") String userId, @RequestBody UserDTO userDTO) {
        boolean result = userService.updatePassword(userId, userDTO.getPassword());
        if (result) {
            return ResponseEntity.ok("ok"); // 성공 시 200 OK 반환
        } else {
            return ResponseEntity.badRequest().body("failed"); // 실패 시 400 Bad Request 반환
        }
    }

    //최종 폰넘버, 이메일 수정
    @PutMapping("/phoneoremail/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable("id") String userId, @RequestBody UserDTO userDTO) {
        UserDTO updatedUserDTO = userService.update(userId, userDTO.getPhoneNb(), userDTO.getEmail());

        if (updatedUserDTO != null) {
            return ResponseEntity.ok(updatedUserDTO); // 성공 시 수정된 사용자 정보 반환
        } else {
            return ResponseEntity.badRequest().build(); // 실패 시 400 Bad Request 반환
        }
    }


//    //폰넘버, 이메일수정  됨..
//    @PutMapping("/phoneoremail/{id}")
//    public void update(@PathVariable("id") String userId, @RequestBody UserDTO userDTO) {
//        userService.update(userId, userDTO.getPhoneNb(), userDTO.getEmail());
//    }


    // 최종 삭제 (활동계정 탈퇴로 바꾸기) 됨 홈페이지에서 null값으로 반환됨 데이터 안보내주는거같은데
    //- [DELETE /users/{id}, {탈퇴 데이터}] user 테이블에 있는 회원 정보 중 status를 '탈퇴'로 변경하는 것이 탈퇴처리이며 그와 동시에 us_del 테이블에 해당 유저의 탈퇴 정보를 추가함.
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") String userId, @RequestBody UserDelDTO userDelDTO) {
        UserEntity userEntity = userService.loadUserByUserId(userId);
        userEntity.setStatus("탈퇴회원");
        userRepository.save(userEntity);

        UserDelEntity userDelEntity = new UserDelEntity();
        userDelEntity.setUserEntity(userEntity);
        userDelEntity.setUserId(userId);
        userDelEntity.setText(userDelDTO.getText());

        userDelService.saveDeletedUser(userDelEntity);
        return "User with userId " + userId + " has been deleted.";
    }

}


