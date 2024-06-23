package com.sparta.fooddeliveryapp.domain.review.controller;

import com.sparta.fooddeliveryapp.domain.review.dto.ReviewCreateRequestDto;
import com.sparta.fooddeliveryapp.domain.review.dto.ReviewResponseDto;
import com.sparta.fooddeliveryapp.domain.review.service.ReviewService;
import com.sparta.fooddeliveryapp.domain.user.entity.User;
import com.sparta.fooddeliveryapp.domain.user.entity.UserRoleEnum;
import com.sparta.fooddeliveryapp.domain.user.repository.UserRepository;
import com.sparta.fooddeliveryapp.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/review")
public class ReviewController {

    private final ReviewService reviewService;

    // 임시 user 생성
    private final UserRepository userRepository;
    private User getTempUser() {
        return userRepository.findById(1L).orElse(null);
    }

    @PostMapping
    public ResponseEntity<String> createReview(
//            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody ReviewCreateRequestDto requestDto) {
        reviewService.createReview(getTempUser(), requestDto);
        return ResponseEntity.status(HttpStatus.OK).body("리뷰 등록 완료");
    }

    @GetMapping
    public ResponseEntity<List<ReviewResponseDto>> getReviews(
//            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return reviewService.getReviews(getTempUser());
    }
}
