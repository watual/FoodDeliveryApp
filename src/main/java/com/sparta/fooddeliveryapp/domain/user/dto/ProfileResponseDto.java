package com.sparta.fooddeliveryapp.domain.user.dto;

import lombok.Getter;

@Getter
public class ProfileResponseDto {

    private Long userId;
    private String name;
    private String nickname;
    private String address;
    private String phone;
    private String email;
    private String intro;

    public ProfileResponseDto(Long userId, String name, String nickname, String address, String phone, String email, String intro) {
        this.userId = userId;
        this.name = name;
        this.nickname = nickname;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.intro = intro;
    }
}
