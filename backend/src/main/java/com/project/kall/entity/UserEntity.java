package com.project.kall.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.kall.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@Table(name = "user")
public class UserEntity implements UserDetails {
    @Id
    @Column(length = 15, name = "id")
    //private String id;
    private String userId;


    @OneToMany(mappedBy = "userEntity" , fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JsonIgnore
    private List<UserDelEntity> userDelEntities = new ArrayList<>();
    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<UsAddressEntity> usAddressEntities = new ArrayList<>();
    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<OrderEntity> orderEntities = new ArrayList<>();
    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<RefundEntity> refundEntities = new ArrayList<>();
    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ProductQEntity> productQEntities = new ArrayList<>();
    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ReviewEntity> reviewEntities = new ArrayList<>();
    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ReviewCmtEntity> reviewCmtEntities = new ArrayList<>();
    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<CartEntity> cartEntities = new ArrayList<>();
    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<WishListEntity> wishListEntities = new ArrayList<>();
    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<UsQEntity> usQEntities = new ArrayList<>();
    @Column(nullable = false, length = 10)
    private String name;
    @Column(nullable = false, length = 150)
    private String password;
    @Column(nullable = false, length = 15)
    private String phoneNb;
    @Column(nullable = false, length = 40)
    private String email;
    @Column(length = 20)
    private String birthday;
    @Column(length = 30)
    private String root;
    @Column(length = 1)
    private String eventagree;
    @Column(length = 10)
    private String role = "user";
    @Column(length = 8)
    private Integer mileage = 3000;
    @CreationTimestamp
    private LocalDateTime date= LocalDateTime.now();
    @Column(length = 20)
    private String status= "활동계정";

    @Column(length = 50)
    private String tmp_2;

    public static UserEntity toUserEntity(UserDTO userDTO) {
        UserEntity userEntity = new UserEntity();

        userEntity.setUserId(userDTO.getUserId());
        userEntity.setName(userDTO.getName());
        userEntity.setPassword(userDTO.getPassword());
        userEntity.setPhoneNb(userDTO.getPhoneNb());
        userEntity.setEmail(userDTO.getEmail());
        userEntity.setBirthday(userDTO.getBirthday());
        userEntity.setRoot(userDTO.getRoot());
        userEntity.setEventagree(userDTO.getEventagree());
        userEntity.setRole(userDTO.getRole());
        userEntity.setMileage(userDTO.getMileage());
        userEntity.setDate(userDTO.getDate());
        userEntity.setStatus(userDTO.getStatus());
        return userEntity;
    }

    public void updateUser(String phoneNb, String email) {
        this.phoneNb = phoneNb;
        this.email = email;
    }

    public String getId() {

        return this.userId;
    }

    @Override
    public String getUsername() {
        return userId; // 사용자의 아이디인 userId 값을 반환
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 사용자의 권한 정보를 반환하는 코드를 작성합니다.
        // 예를 들어, "ROLE_USER" 권한을 가진 사용자라면 아래와 같이 작성합니다.
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("USER"));
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        // 계정이 만료되었는지 여부를 반환하는 코드를 작성합니다.
        return true; // 계정 만료되지 않음
    }

    @Override
    public boolean isAccountNonLocked() {
        // 계정이 잠겼는지 여부를 반환하는 코드를 작성합니다.
        return true; // 계정 잠기지 않음
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // 사용자의 인증 정보가 만료되었는지 여부를 반환하는 코드를 작성합니다.
        return true; // 인증 정보 만료되지 않음
    }

    @Override
    public boolean isEnabled() {
        // 사용자가 활성화되었는지 여부를 반환하는 코드를 작성합니다.
        return true; // 사용자 활성화됨
    }
}