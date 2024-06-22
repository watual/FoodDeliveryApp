package com.sparta.fooddeliveryapp.domain.auth.service;

import com.sparta.fooddeliveryapp.domain.user.entity.User;
import com.sparta.fooddeliveryapp.domain.user.repository.UserRepository;
import com.sparta.fooddeliveryapp.global.security.JwtUtil;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "AuthService")
@Component
public class AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String refreshToken = request.getHeader("RefreshToken").substring(7);

        if(jwtUtil.validateToken(refreshToken)) {
            String loginId = jwtUtil.extractLoginId(refreshToken);
            User user = userRepository.findByLoginId(loginId).orElseThrow(NullPointerException::new);

            // create new token
            String newAccessToken = jwtUtil.createAccessToken(user.getLoginId(), user.getRole());
            String newRefreshToken = jwtUtil.createRefreshToken(user.getLoginId());

            user.setRefreshToken(newRefreshToken);
            response.setHeader("Authorization", newAccessToken);
            response.setHeader("RefreshToken", newRefreshToken);

            log.info("토큰이 새로 발급되었습니다.");
        }
    }
}
