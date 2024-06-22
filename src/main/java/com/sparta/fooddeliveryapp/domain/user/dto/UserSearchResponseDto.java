package com.sparta.fooddeliveryapp.domain.user.dto;

import com.sparta.fooddeliveryapp.domain.user.entity.UserStatusEnum;
import lombok.Getter;

@Getter
public class UserSearchResponseDto {

    private String nickname;
    private String email;
    private String intro;
    private UserStatusEnum status;

    public UserSearchResponseDto(String nickname, String email, String intro, UserStatusEnum status) {
        this.nickname = nickname;
        this.email = email;
        this.intro = intro;
        this.status = status;
    }
}
