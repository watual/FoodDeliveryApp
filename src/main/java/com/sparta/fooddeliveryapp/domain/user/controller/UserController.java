package com.sparta.fooddeliveryapp.domain.user.controller;

import com.sparta.fooddeliveryapp.domain.user.dto.DeactivateRequestDto;
import com.sparta.fooddeliveryapp.domain.user.dto.SignupRequestDto;
import com.sparta.fooddeliveryapp.domain.user.dto.UpdatePasswordRequestDto;
import com.sparta.fooddeliveryapp.domain.user.dto.UpdateProfileRequestDto;
import com.sparta.fooddeliveryapp.domain.user.service.UserService;
import com.sparta.fooddeliveryapp.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupRequestDto requestDto) {
        userService.signup(requestDto);
        return ResponseEntity.status(HttpStatus.OK).body("회원가입 완료");
    }

    @PutMapping("/deactivate")
    public ResponseEntity<String> deactivateUser(@RequestBody DeactivateRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        userService.deactivateUser(requestDto, userDetails.getUser());
        // 로그아웃 시키기
        return ResponseEntity.status(HttpStatus.OK).body("회원 비활성화 완료");
    }

    @PatchMapping("/update-profile")
    public ResponseEntity<String> updateProfile(@RequestBody UpdateProfileRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        userService.updateProfile(requestDto, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body("프로필 수정 완료");
    }

//    @PutMapping("/update-password")
//    public ResponseEntity<String> updatePassword(@RequestBody UpdatePasswordRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
//        userService.updatePassword(requestDto, userDetails.getUser());
//        return ResponseEntity.status(HttpStatus.OK).body("비밀번호 수정 완료");
//    }


}
