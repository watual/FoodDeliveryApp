package com.sparta.fooddeliveryapp.domain.review.controller;

import com.sparta.fooddeliveryapp.domain.review.dto.ReviewCreateRequestDto;
import com.sparta.fooddeliveryapp.domain.review.service.ReviewService;
import com.sparta.fooddeliveryapp.domain.user.entity.User;
import com.sparta.fooddeliveryapp.domain.user.entity.UserRoleEnum;
import com.sparta.fooddeliveryapp.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/review")
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<String> createReview(
//            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody ReviewCreateRequestDto requestDto) {
        // 임시 데이터
        User user = User
                .builder()
                .userId(1L)
                .loginId("testUser")
                .password("testPWD")
                .role(UserRoleEnum.USER)
                .build();
        reviewService.createReview(user, requestDto);
        return ResponseEntity.status(HttpStatus.OK).body("리뷰 등록 완료");
    }
}
