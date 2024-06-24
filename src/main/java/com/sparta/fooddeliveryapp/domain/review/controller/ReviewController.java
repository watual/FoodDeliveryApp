package com.sparta.fooddeliveryapp.domain.review.controller;

import com.sparta.fooddeliveryapp.domain.review.dto.ReviewCreateRequestDto;
import com.sparta.fooddeliveryapp.domain.review.dto.ReviewResponseDto;
import com.sparta.fooddeliveryapp.domain.review.dto.ReviewUpdateRequestDto;
import com.sparta.fooddeliveryapp.domain.review.entity.Review;
import com.sparta.fooddeliveryapp.domain.review.service.ReviewService;
import com.sparta.fooddeliveryapp.domain.user.entity.User;
import com.sparta.fooddeliveryapp.domain.user.repository.UserRepository;
import com.sparta.fooddeliveryapp.global.common.ResponseDto;
import com.sparta.fooddeliveryapp.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
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

    @PostMapping
    public ResponseEntity<ResponseDto> createReview(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody ReviewCreateRequestDto requestDto) {
        reviewService.createReview(userDetails.getUser(), requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseDto.builder()
                        .status(HttpStatus.OK)
                        .message("리뷰 등록 완료")
                        .build());
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<ReviewResponseDto>> getReviews(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return reviewService.getReviews(userDetails.getUser());
    }

    @PatchMapping
    public ResponseEntity<ResponseDto> updateReview(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody ReviewUpdateRequestDto requestDto) {
        reviewService.updateReview(userDetails.getUser(), requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseDto.builder()
                        .status(HttpStatus.OK)
                        .message("리뷰 수정 완료")
                        .build());
    }

    @DeleteMapping
    public ResponseEntity<ResponseDto> deleteReview(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam Long reviewId
    ) {
        reviewService.deleteReview(userDetails.getUser(), reviewId);
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseDto.builder()
                        .status(HttpStatus.OK)
                        .message("리뷰 삭제 완료")
                        .build()
        );
    }
}
