package com.sparta.fooddeliveryapp.domain.user.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateProfileRequestDto {

    String name;
    String nickname;
    String address;

    @Size(max=30, message = "소개는 최대 30자입니다.")
    String intro;
}
