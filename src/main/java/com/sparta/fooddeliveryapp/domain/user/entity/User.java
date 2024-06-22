package com.sparta.fooddeliveryapp.domain.user.entity;


import com.sparta.fooddeliveryapp.global.common.TimeStamped;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "user")
@NoArgsConstructor
public class User extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String loginId;

    private String password;

    private String name;

    private String nickname;

    private String address;

    private String phone;

    private String email;

    private String intro;

    private UserRoleEnum role;

    private UserStatus status;

    private String refreshToken;

    public void setRefreshToken(String refreshToken){
        this.refreshToken = refreshToken;
    }
}
