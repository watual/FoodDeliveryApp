package com.sparta.fooddeliveryapp.domain.review.service;

import com.sparta.fooddeliveryapp.domain.order.entity.Orders;
import com.sparta.fooddeliveryapp.domain.order.repository.OrderRepository;
import com.sparta.fooddeliveryapp.domain.review.dto.ReviewCreateRequestDto;
import com.sparta.fooddeliveryapp.domain.review.dto.ReviewResponseDto;
import com.sparta.fooddeliveryapp.domain.review.dto.ReviewUpdateRequestDto;
import com.sparta.fooddeliveryapp.domain.review.entity.Review;
import com.sparta.fooddeliveryapp.domain.review.repository.ReviewRepository;
import com.sparta.fooddeliveryapp.domain.user.entity.User;
import com.sparta.fooddeliveryapp.global.error.exception.InsufficientOrdersException;
import com.sparta.fooddeliveryapp.global.error.exception.ReviewException;
import com.sparta.fooddeliveryapp.global.error.exception.UserMismatchException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public void createReview(User user, ReviewCreateRequestDto requestDto) {
        Long orderId = requestDto.getOrdersId();
        // 주문내역 조회
        Orders orders = orderRepository.findById(orderId).orElseThrow(
                () -> new InsufficientOrdersException("해당 주문을 찾을 수 없습니다.")
        );
        // 사용자 일치 확인
        if (!Objects.equals(user.getUserId(), orders.getUser().getUserId())) {
            throw new ReviewException("주문내역이 현재 사용자의 주문내역이 아닙니다");
        }
        // 작성된 이력이 있는지 확인
        if(reviewRepository.existsByOrdersId(orderId)) {
            throw new ReviewException("이미 리뷰를 작성했습니다");
        }

        Review review = Review.builder()
                .user(user)
                .store(orders.getStore())
                .ordersId(orders.getOrdersId())
                .content(requestDto.getContent())
                .rate(requestDto.getRate())
                .build();

        reviewRepository.save(review);
        log.info("Complete createReview Service");
    }

    public ResponseEntity<List<ReviewResponseDto>> getReviews(User user) {
        List<Review> myReviewList = reviewRepository.findAllByUser(user).orElseThrow(
                () -> new NullPointerException("작성한 리뷰가 없습니다")
        );
        return ResponseEntity.status(HttpStatus.OK).body(myReviewList.stream().map(
                review -> ReviewResponseDto.builder()
                        .reviewId(review.getReviewId())
                        .userName(review.getUser().getName())
                        .ordersId(review.getOrdersId())
                        .content(review.getContent())
                        .rate(review.getRate())
                        .build()
                        ).toList()
        );
    }

    @Transactional
    public void updateReview(User tempUser, ReviewUpdateRequestDto requestDto) {
        Review review = reviewRepository.findById(requestDto.getReviewId()).orElseThrow(
                () -> new NullPointerException("존재하지 않는 리뷰입니다")
        );

        if(!Objects.equals(review.getUser().getUserId(), tempUser.getUserId())) {
            throw new UserMismatchException("접근 권한이 없습니다");
        }

        review.update(
                requestDto.getContent(),
                requestDto.getRate());
    }

    public void deleteReview(User tempUser, Long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(
                () -> new NullPointerException("존재하지 않는 리뷰입니다")
        );

        if(!Objects.equals(review.getUser().getUserId(), tempUser.getUserId())) {
            throw new UserMismatchException("접근 권한이 없습니다");
        }

        reviewRepository.delete(review);
    }
}
