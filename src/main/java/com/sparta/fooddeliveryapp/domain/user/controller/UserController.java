package com.sparta.fooddeliveryapp.domain.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.fooddeliveryapp.domain.user.dto.*;
import com.sparta.fooddeliveryapp.domain.user.service.KakaoService;
import com.sparta.fooddeliveryapp.domain.user.service.UserService;
import com.sparta.fooddeliveryapp.global.security.JwtUtil;
import com.sparta.fooddeliveryapp.global.security.UserDetailsImpl;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
  
    private final KakaoService kakaoService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupRequestDto requestDto) {
        userService.signup(requestDto);
        return ResponseEntity.status(HttpStatus.OK).body("회원가입 완료");
    }

    @PutMapping("/deactivate")
    public ResponseEntity<String> deactivateUser(@RequestBody DeactivateRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        userService.deactivateUser(requestDto, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body("회원 비활성화 완료");
    }

    @PatchMapping("/update-profile/{userId}")
    public ResponseEntity<String> updateProfile(@RequestBody UpdateProfileRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long userId){
        userService.updateProfile(requestDto, userDetails.getUser(), userId);
        return ResponseEntity.status(HttpStatus.OK).body("프로필 수정 완료");
    }

    @GetMapping("/profile")
    public ProfileResponseDto getProfile(@Valid @AuthenticationPrincipal UserDetailsImpl userDetails){
        ProfileResponseDto res = userService.getProfile(userDetails.getUser());
        return res;
    }

    @GetMapping("/profile/{nickname}")
    public List<UserSearchResponseDto> UserSearch(@PathVariable String nickname){
        List<UserSearchResponseDto> res = userService.UserSearch(nickname);
        return res;
    }

    @PutMapping("/update-password")
    public ResponseEntity<String> updatePassword(@RequestBody UpdatePasswordRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        userService.updatePassword(requestDto, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body("비밀번호 수정 완료");
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        userService.logout(userDetails.getUser().getUserId());
        return ResponseEntity.status(HttpStatus.OK).body("로그아웃 완료");
    }
  
  @GetMapping("/kakao/callback")
    public ResponseEntity<String> kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
        log.info("kakaoLogin");
        String token = kakaoService.kakaoLogin(code);

        Cookie cookie = new Cookie(JwtUtil.AUTHORIZATION_HEADER, token);
        cookie.setPath("/");
        response.addCookie(cookie);

        return ResponseEntity.ok().body("로그인 성공");
    }

    @GetMapping("/kakao")
    public String kakaoLoginPage() {
        return "https://kauth.kakao.com/oauth/authorize?client_id=6bd39095290563c7275db8ec2daeda3b&redirect_uri=http://localhost:8080/api/user/kakao/callback&response_type=code";
    }
}
