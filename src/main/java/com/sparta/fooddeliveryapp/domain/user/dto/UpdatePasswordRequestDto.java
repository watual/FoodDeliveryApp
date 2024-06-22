package com.sparta.fooddeliveryapp.domain.user.dto;

import lombok.Getter;

@Getter
public class UpdatePasswordRequestDto {
    String oldPassword;
    String newPassword;
}
