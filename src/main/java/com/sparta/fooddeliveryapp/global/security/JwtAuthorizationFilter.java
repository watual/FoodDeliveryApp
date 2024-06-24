package com.sparta.fooddeliveryapp.global.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j(topic = "JWT 검증, 인가")
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = jwtUtil.getJwtFromHeader(request);

        // 로그인의 경우 이미 permit. 하지만, Header 에 JWT 를 들고 있으면 무조건 검사를 시행하는게 문제
        if(StringUtils.hasText(token)
                // 인가 요청시 확인 없이 넘어가야하는 주소들 추가
                && !request.getRequestURL().toString().contains("/api/auth/")
                && !request.getRequestURL().toString().contains("/api/users/login")
                && !request.getRequestURL().toString().contains("/api/users/signup")
                && !request.getRequestURL().toString().contains("/api/users/profile/")
        ){
            if(!jwtUtil.validateToken(token)){
                log.error("Token error");
                return;
            }

            Claims info = jwtUtil.getUserInfoFromToken(token);

            try{
                setAuthentication(info.getSubject());   // permit
            } catch(Exception e) {
                log.error(e.getMessage());
                return;
            }
        }
        log.info("AuthorizationFilter");
        filterChain.doFilter(request, response);
    }

    // authenticate
    public void setAuthentication(String loginId) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = createAuthentication(loginId);
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
    }

    // create authentication
    private Authentication createAuthentication(String loginId) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginId);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

}
