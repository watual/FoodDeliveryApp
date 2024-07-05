package com.sparta.fooddeliveryapp.domain.review.controller;

import com.sparta.fooddeliveryapp.domain.review.dto.ReviewCreateRequestDto;
import com.sparta.fooddeliveryapp.domain.review.dto.ReviewResponseDto;
import com.sparta.fooddeliveryapp.domain.review.dto.ReviewUpdateRequestDto;
import com.sparta.fooddeliveryapp.domain.review.entity.Review;
import com.sparta.fooddeliveryapp.domain.review.service.ReviewService;
import com.sparta.fooddeliveryapp.domain.store.dto.StoreResponseDto;
import com.sparta.fooddeliveryapp.domain.store.entity.Store;
import com.sparta.fooddeliveryapp.domain.user.entity.User;
import com.sparta.fooddeliveryapp.domain.user.repository.UserRepository;
import com.sparta.fooddeliveryapp.global.common.ResponseDto;
import com.sparta.fooddeliveryapp.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
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

    // 내가 좋아하는 게시글 목록 조회기능 추가하기
    @GetMapping("/myLike")
    public ResponseEntity<List<ReviewResponseDto>> myLikeStores(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        log.info("myLikeStores");
        Page<Review> reviewPage = reviewService.myLikeReviews(userDetails.getUser(), page, size);
        if (reviewPage.isEmpty()) {
            throw new NullPointerException("좋아요를 남긴 리뷰가 없습니다");
        }

        List<ReviewResponseDto> response = reviewPage.stream().map(review -> new ReviewResponseDto(
                review.getReviewId(),
                review.getUser().getName(),
                review.getOrdersId(),
                review.getContent(),
                review.getRate(),
                review.getUserLikeCount()
        )).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
