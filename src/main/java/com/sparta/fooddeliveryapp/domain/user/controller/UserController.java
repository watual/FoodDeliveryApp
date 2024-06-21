package com.sparta.fooddeliveryapp.domain.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.fooddeliveryapp.domain.user.service.KakaoService;
import com.sparta.fooddeliveryapp.domain.user.service.UserService;
import com.sparta.fooddeliveryapp.global.security.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final KakaoService kakaoService;

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
    @GetMapping("/kakao1")
    public String kakaoLoginPage1() {
        return "https://kauth.kakao.com/oauth/authorize?client_id=6bd39095290563c7275db8ec2daeda3b&redirect_uri=http://localhost:8080/api/user/kakao/callback&response_type=code";
    }
    @GetMapping("/kakao2")
    public String kakaoLoginPage2() {
        log.info("asdfasdfasdfasdfasdf");
        return "https://kauth.kakao.com/oauth/authorize?client_id=6bd39095290563c7275db8ec2daeda3b&redirect_uri=http://localhost:8080/api/user/kakao/callback&response_type=code";
    }

}
