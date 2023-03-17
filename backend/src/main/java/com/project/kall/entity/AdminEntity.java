//package com.project.kall.entity;
//
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import org.hibernate.annotations.CreationTimestamp;
//import org.hibernate.annotations.DynamicInsert;
//
//import javax.persistence.*;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//@Entity
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@DynamicInsert
//@Table(name = "user")
//public class AdminEntity {
//    @Id
//    @Column(length = 15, name = "id")
//    private String userId;
//
//    @OneToMany(mappedBy = "userEntity" , fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
//    private List<UserDelEntity> userDelEntities = new ArrayList<>();
//    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
//    private List<UsAddressEntity> usAddressEntities = new ArrayList<>();
//    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
//    private List<OrderEntity> orderEntities = new ArrayList<>();
//    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
//    private List<RefundEntity> refundEntities = new ArrayList<>();
//    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
//    private List<ProductQEntity> productQEntities = new ArrayList<>();
//    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
//    private List<ReviewEntity> reviewEntities = new ArrayList<>();
//    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
//    private List<ReviewCmtEntity> reviewCmtEntities = new ArrayList<>();
//    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
//    private List<CartEntity> cartEntities = new ArrayList<>();
//    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
//    private List<WishListEntity> wishListEntities = new ArrayList<>();
//    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
//    private List<UsQEntity> usQEntities = new ArrayList<>();
//    @Column(nullable = false, length = 10)
//    private String name;
//    @Column(nullable = false, length = 150)
//    private String password;
//    @Column(nullable = false, length = 15)
//    private String phoneNb;
//    @Column(nullable = false, length = 40)
//    private String email;
//    @Column(length = 20)
//    private String birthday;
//    @Column(length = 30)
//    private String root;
//    @Column(length = 1)
//    private String eventagree;
//    @Column(length = 10)
//    private String role = "user";
//    @Column(length = 8)
//    private Integer mileage = 3000;
//    @CreationTimestamp
//    private LocalDateTime date= LocalDateTime.now();
//    @Column(length = 20)
//    private String status= "활동계정";
//    @Column(length = 20)
//    private String tmp_2;
//
//}
