package com.sparta.fooddeliveryapp.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdatePasswordRequestDto {
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*()-_=+\\\\|\\[{\\]};:'\",<.>/?]).{10,}$", message = "대소문자 포함 영문 + 숫자 + 특수문자를 최소 1글자씩 포함해야 합니다.")
    @Size(min = 8, max=15, message = "비밀번호는 8~15자 사이로 해주세요.")
    @NotBlank(message = "oldPassword는 필수입니다.")
    String oldPassword;

    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*()-_=+\\\\|\\[{\\]};:'\",<.>/?]).{10,}$", message = "대소문자 포함 영문 + 숫자 + 특수문자를 최소 1글자씩 포함해야 합니다.")
    @Size(min = 8, max=15, message = "비밀번호는 8~15자 사이로 해주세요.")
    @NotBlank(message = "newPassword는 필수입니다.")
    String newPassword;
}
