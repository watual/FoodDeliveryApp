package com.sparta.fooddeliveryapp.domain.user.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "user")
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "login_id", nullable = false)
    private String loginId;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "intro")
    private String intro;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private UserRoleEnum role;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private UserStatusEnum status;

    @Column(name = "refresh_token")
    private String refreshToken;

    // image 가져오기
    // 생성 및 수정 시간은 타 클래스 implement 가져오는걸로

    public void setRefreshToken(String refreshToken){
        this.refreshToken = refreshToken;
    }
    public void setStatusDeactivated(){ this.status = UserStatusEnum.DEACTIVATED; }

    public User(String loginId, String password, String name, String nickname, String address, String phone,
                String email, String intro, UserRoleEnum role, UserStatusEnum status) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.intro = intro;
        this.role = role;
        this.status = status;
    }
}
