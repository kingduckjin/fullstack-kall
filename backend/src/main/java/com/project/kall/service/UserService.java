package com.project.kall.service;

//import com.project.kall.config.jwt.JwtProps;
import com.project.kall.repository.UserRepository;
import com.project.kall.dto.UserDTO;
import com.project.kall.entity.UserEntity;
import lombok.RequiredArgsConstructor;
        import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.*;
//import user.config.jwt.*;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final JwtService jwtService;

    private PasswordEncoder passwordEncoder;


    // 최종 회원가입

    public String save(UserDTO userDTO) {
        System.out.println(userDTO);
        if (isDuplicatedId(userDTO.getUserId())) {
            throw new IllegalArgumentException("이미 가입된 회원입니다.");
        }
        if (isDuplicatedEmail(userDTO.getEmail())) {
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        }
        UserEntity userEntity = UserEntity.toUserEntity(userDTO);
        String saveId = userRepository.save(userEntity).getUserId();
        return saveId;
    }

    // 최종 중복확인
    public boolean isDuplicatedId(String userId) {
        Optional<UserEntity> optionalUserEntity = userRepository.findById(userId);
        return optionalUserEntity.isPresent();
    }
    // 최종 이메일중복확인
    public boolean isDuplicatedEmail(String email) {
        Optional<UserEntity> optionalUserEntity = userRepository.findByEmail(email);
        return optionalUserEntity.isPresent();
    }

    // 최종 로그인
    public Map<String, Object> login(UserDTO userDTO) {
        System.out.println(userDTO);
        // UserDto로부터 아이디와 비밀번호를 가져옵니다.
        String userId = userDTO.getUserId();
        String password = userDTO.getPassword();


        // 아이디와 비밀번호가 null이면 로그인 실패로 간주합니다.
        if (userId == null || password == null) {
            return null;
        }

        // 아이디와 비밀번호를 이용하여 사용자 정보를 가져옵니다.
        UserEntity userEntity = userRepository.findByIdAndPassword(userId, password);
//        Optional<UserEntity> userEntity = userRepository.findById(userId);
//        System.out.println(userEntity.toString());

        // 사용자 정보가 없으면 로그인 실패로 간주합니다.
        if (userEntity == null) {
            return null;
        }

        //JWT 토큰을 생성하여 반환합니다.
        String jwtToken = jwtService.createToken(userEntity.getUserId(), userEntity.getRole(), 1000 * 60 * 30);
        System.out.println(jwtToken);

        Map<String, Object> userToken = new HashMap<>();
        userEntity.setPassword(null);

        userToken.put("user", userEntity);
        userToken.put("token", jwtToken);

        /**
         * 리턴해줄때
         * {
         * user: 유저 정보 객체
         *
         */
        return userToken;
    }

    //UserDetailsService 구현이건 role
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Optional<UserEntity> userEntity = userRepository.findById(userId);
        if (!userEntity.isPresent()) {
            throw new UsernameNotFoundException(userId);
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        return new org.springframework.security.core.userdetails.User(userEntity.get().getUserId(), userEntity.get().getPassword(), authorities);
    }

    // 마이페이지 수정 전 비번 확인하기
    public UserEntity loadUserByUserId(String userId) throws UsernameNotFoundException {
        Optional<UserEntity> optionalUserEntity = userRepository.findById(userId);
        return optionalUserEntity.orElseThrow(() -> new UsernameNotFoundException("User not found with UserId: " + userId));
    }
    public boolean checkPassword(String userId, String password) {
        UserEntity userEntity = loadUserByUserId(userId);
        return passwordEncoder.matches(password, userEntity.getPassword());
    }



    //마이페이지 수정 전 비밀번호 재확인
//    public UserEntity loadUserByUserId(String userId) throws UsernameNotFoundException {
//        Optional<UserEntity> optionalUserEntity = userRepository.findById(userId);
//        UserEntity userEntity = optionalUserEntity.orElseThrow(() -> new UsernameNotFoundException("User not found with UserId: " + userId));
//        return userEntity;
//    }
//    public boolean checkPassword(String UserId, String password) {
//        Optional<UserEntity> userEntity = userRepository.findById(UserId);
//        if (userEntity == null) {
//            throw new UsernameNotFoundException("User not found with UserId: " + UserId);
//        }
//        return passwordEncoder.matches(password, userEntity.get().getPassword());
//    }

    // 최종 아이디 찾기
    public String findIdByEmail(String name, String email) {
        UserEntity userEntity = userRepository.findByNameAndEmail(name, email);
        if (userEntity == null) {
            throw new RuntimeException("User not found");
        }
        return userEntity.getId();
    }
    public String findIdByPhone(String name, String phoneNb) {
        UserEntity userEntity = userRepository.findByNameAndPhoneNb(name, phoneNb);
        if (userEntity == null) {
            throw new RuntimeException("User not found");
        }
        return userEntity.getId();
    }

    // 비밀번호 찾기
    public String changePassword(String userId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String newPassword = generateRandomPassword();
        userEntity.setPassword(newPassword);
        userRepository.save(userEntity);

        return "Your new password is: " + newPassword;
    }
    private String generateRandomPassword() {
        int passwordLength = 7;
        String allowedChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < passwordLength; i++) {
            int randomIndex = random.nextInt(allowedChars.length());
            sb.append(allowedChars.charAt(randomIndex));
        }

        return sb.toString();
    }

    // 비밀번호 찾기 사용인데 태현이가 페이지 수정하면 그때 쓸거

//    public String findPasswordByEmail(String UserId, String email) {
//        UserEntity userEntity = userRepository.findByUserIdAndEmail(UserId, email);
//        if (userEntity == null) {
//            throw new RuntimeException("User not found");
//        }
//
//        String newPassword = generateRandomPassword();
//        changePassword(userEntity.getId(), newPassword);
//
//        return newPassword;
//    }
//
//    public String findPasswordByPhone(String UserId, String phoneNb) {
//        UserEntity userEntity = userRepository.findByUserIdAndPhone(UserId, phoneNb);
//        if (userEntity == null) {
//            throw new RuntimeException("User not found");
//        }
//
//        String newPassword = generateRandomPassword();
//        changePassword(userEntity.getId(), newPassword);
//
//        return newPassword;
//    }

    //비밀번호 수정
    @Transactional
    public boolean updatePassword(String userId, String password) {
        UserEntity userEntity = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        userEntity.setPassword(password);
        userRepository.save(userEntity);
        return false;
    }


    // 최종 폰넘버, 이메일 수정
    @Transactional
    public UserDTO update(String userId, String phoneNb, String email) {
        UserEntity userEntity = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        if (phoneNb != null) {
            userEntity.setPhoneNb(phoneNb);
        }

        if (email != null) {
            userEntity.setEmail(email);
        }

        userRepository.save(userEntity);

        // 수정된 UserDTO 객체 반환
        return UserDTO.toUserDTOnopass(userEntity);
    }


    //관리자
    // 최종 리스트 뽑기
    public List<UserDTO> findAll() {
        List<UserEntity> userEntityList = userRepository.findAll();
        List<UserDTO> userDTOList = new ArrayList<>();
        for(UserEntity user:userEntityList){
            userDTOList.add(UserDTO.toUserDTO(user));
        }
        return userDTOList;
    }

    // 최종 개별데이터 보기
    public UserDTO findById(String userId){
        Optional<UserEntity> optionalUserEntity = userRepository.findById(userId);
        if(optionalUserEntity.isPresent()){
            UserEntity userEntity = optionalUserEntity.get();
            return UserDTO.toUserDTO(userEntity);
        }else{
            return null;
        }
    }
    //유저 데이터 수정
    public UserDTO updateUser(String userId, UserDTO userDTO) {
        Optional<UserEntity> userEntity = userRepository.findById(userId);
        if (userEntity.isPresent()) {
            UserEntity entity = userEntity.get();

            // Update only non-null fields in the DTO
            if (userDTO.getName() != null) {
                entity.setName(userDTO.getName());
            }
            if (userDTO.getPassword() != null) {
                entity.setPassword(userDTO.getPassword());
            }
            if (userDTO.getPhoneNb() != null) {
                entity.setPhoneNb(userDTO.getPhoneNb());
            }
            if (userDTO.getEmail() != null) {
                entity.setEmail(userDTO.getEmail());
            }
            if (userDTO.getBirthday() != null) {
                entity.setBirthday(userDTO.getBirthday());
            }
            if (userDTO.getRoot() != null) {
                entity.setRoot(userDTO.getRoot());
            }
            if (userDTO.getEventagree() != null) {
                entity.setEventagree(userDTO.getEventagree());
            }
            if (userDTO.getRole() != null) {
                entity.setRole(userDTO.getRole());
            }
            if (userDTO.getMileage() != null) {
                entity.setMileage(userDTO.getMileage());
            }
            if (userDTO.getDate() != null) {
                entity.setDate(userDTO.getDate());
            }
            if (userDTO.getStatus() != null) {
                entity.setStatus(userDTO.getStatus());
            }

            // Save the updated entity to the database
            UserEntity updatedUserEntity = userRepository.save(entity);

            // Convert the updated entity to DTO and return it
            UserDTO updatedUserDTO = UserDTO.toUserDTO(updatedUserEntity);
            return updatedUserDTO;
        } else {
            throw new RuntimeException("유저를 찾을 수 없습니다.");
        }
    }
    // 삭제 기능은 위에 유저삭제와 동일 생략

    // 다수의 유저 삭제
//    @Transactional
//    public void deleteUsers(List<String> userIds) {
//        for (String userId : userIds) {
//            UserEntity userEntity = userRepository.findById(userId)
//                    .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
//            userRepository.delete(userEntity);
//        }
//    }


}
