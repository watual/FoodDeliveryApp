package com.sparta.fooddeliveryapp.global.security;

import com.sparta.fooddeliveryapp.domain.user.repository.UserRepository;
import com.sparta.fooddeliveryapp.global.error.exception.LoggedOutUserException;
import com.sparta.fooddeliveryapp.global.error.exception.UserNotFoundException;
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
import com.sparta.fooddeliveryapp.domain.user.entity.User;

import java.io.IOException;

@Slf4j(topic = "JWT 검증, 인가")
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = jwtUtil.getJwtFromHeader(request);

        log.info("token : " + token);
        // 로그인의 경우 이미 permit. 하지만, Header 에 JWT 를 들고 있으면 무조건 검사를 시행하는게 문제
        if(StringUtils.hasText(token)
                // 인가 요청시 확인 없이 넘어가야하는 주소들 추가
                && !request.getRequestURL().toString().contains("/api/auth")
                && !request.getRequestURL().toString().contains("/api/users/login")
                && !request.getRequestURL().toString().contains("/api/users/signup")
                && !request.getRequestURL().toString().contains("/api/users/profile/")
                && !request.getRequestURL().toString().contains("/api/users/kakao")
        ){
            log.info("토큰 검수 시행");
            if(!jwtUtil.validateToken(token)){
                log.error("Token error");
                return;
            }

            Claims info = jwtUtil.getUserInfoFromToken(token);

            // 로그아웃 된 유저 걸러내기, 로그아웃 하면 accesstoken 살아있어도 인가 막아버림
            String loginId = jwtUtil.extractLoginId(token);
            try{
                User user = userRepository.findByLoginId(loginId).orElseThrow(UserNotFoundException::new);
                if(user.getRefreshToken() == null){
                    throw new LoggedOutUserException();
                }
                setAuthentication(info.getSubject());   // permit
            } catch(Exception e) {
                log.error(e.getMessage());
                response.setContentType("application/json; charset=UTF-8");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
                response.getWriter().flush();
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
