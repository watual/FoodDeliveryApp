package com.sparta.fooddeliveryapp.domain.user.dto;

import com.sparta.fooddeliveryapp.domain.user.entity.UserRoleEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class SignupRequestDto {

    @Pattern(regexp = "^[a-z0-9]+$", message = "소문자 영문 + 숫자만 허용됩니다.")
    @Size(min = 10, max = 20, message = "로그인 ID는 4~10자 사이로 해주세요.")
    @NotBlank(message = "userId는 필수입니다.")
    private String loginId;

    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*()-_=+\\\\|\\[{\\]};:'\",<.>/?]).{10,}$", message = "대소문자 포함 영문 + 숫자 + 특수문자를 최소 1글자씩 포함해야 합니다.")
    @Size(min = 8, max=15, message = "비밀번호는 8~15자 사이로 해주세요.")
    @NotBlank(message = "password는 필수입니다.")
    private String password;

    @NotBlank(message = "name은 필수입니다.")
    private String name;

    @NotBlank(message = "nickname은 필수입니다.")
    private String nickname;

    @NotBlank(message = "주소는 필수입니다.")
    private String address;

    @Pattern(regexp = "^[0-9]+$", message = "숫자만 허용됩니다 예) 01012341234.")
    @Size(min=11, max=11, message = "전화번호는 11자리입니다.")
    @NotBlank(message = "전화번호는 필수입니다.")
    private String phone;

    @Email(message = "올바르지 않은 이메일 형식입니다.")
    @NotBlank(message = "email은 필수입니다.")
    private String email;

    @Size(max=30, message = "소개는 최대 30자입니다.")
    private String intro;

    @NotBlank(message = "USER/SELLER/ADMIN 중에서 선택해주세요.")
    private UserRoleEnum role;

    private String adminToken;
}
