package com.sparta.fooddeliveryapp.domain.user.controller;

import com.sparta.fooddeliveryapp.domain.user.dto.DeactivateRequestDto;
import com.sparta.fooddeliveryapp.domain.user.dto.SignupRequestDto;
import com.sparta.fooddeliveryapp.domain.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/deactivate")
    public ResponseEntity<String> deactivateUser(@RequestBody DeactivateRequestDto requestDto, HttpServletRequest request){
        userService.deactivateUser(requestDto, request);
        // 로그아웃 시키기
        return ResponseEntity.status(HttpStatus.OK).body("회원 비활성화 완료");
    }


}
