package com.sparta.fooddeliveryapp.domain.user.dto;

import lombok.Getter;

@Getter
public class ProfileResponseDto {

    private final Long userId;
    private final String name;
    private final String nickname;
    private final String address;
    private final String phone;
    private final String email;
    private final String intro;
    private final Long reviewLikeCount;
    private final Long storeLikeCount;

    public ProfileResponseDto(
            Long userId,
            String name,
            String nickname,
            String address,
            String phone,
            String email,
            String intro,
            Long reviewLikeCount,
            Long storeLikeCount
    ) {
        this.userId = userId;
        this.name = name;
        this.nickname = nickname;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.intro = intro;
        this.reviewLikeCount = reviewLikeCount;
        this.storeLikeCount = storeLikeCount;
    }
}
