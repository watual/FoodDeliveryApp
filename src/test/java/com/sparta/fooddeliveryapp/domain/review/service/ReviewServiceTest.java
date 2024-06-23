//package com.sparta.fooddeliveryapp.domain.review.service;
//
//import com.sparta.fooddeliveryapp.domain.review.dto.ReviewCreateRequestDto;
//import com.sparta.fooddeliveryapp.domain.review.entity.Orders;
//import com.sparta.fooddeliveryapp.domain.review.repository.OrderRepository;
//import com.sparta.fooddeliveryapp.domain.review.repository.ReviewRepository;
//import com.sparta.fooddeliveryapp.domain.user.entity.User;
//import com.sparta.fooddeliveryapp.domain.user.entity.UserRoleEnum;
//import org.apache.commons.lang3.reflect.FieldUtils;
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class ReviewServiceTest {
//    @Mock
//    private ReviewRepository reviewRepository;
//    @Mock
//    private OrderRepository orderRepository;
//    // Controller
//    // 실제 Review review = reviewService.getReview(reviewId) : 통합 테스트
//    // 가짜 when(reviewService.getReview(reviewId)).then(Review.builder.~~.build()); : 단위 테스트
//
//    @InjectMocks
//    private ReviewService reviewService;
//
//    @Test
//    @DisplayName("리뷰 생성")
//    public void createReview_OK() throws IllegalAccessException {
//        // given
//        // service 매개변수로 들어가는 user : 페이지 현재 사용자
//        User mockUser = new User();
////                .builder()
////                .id(1L)
////                .loginId("애륌")
////                .password("애륌의 장난감")
////                .role(UserRoleEnum.USER)
////                .refreshToken(null)
////                .build();
//        // service 매개변수로 들어가는 ReviewCreateRequestDto : 페이지 사용자가 입력한 리뷰 내용
//        ReviewCreateRequestDto mockRequestDto = new ReviewCreateRequestDto();
//        FieldUtils.writeField(mockRequestDto, "orderGroup", 1L, true);
//        FieldUtils.writeField(mockRequestDto, "content", "리뷰내용", true);
//        FieldUtils.writeField(mockRequestDto, "rate", 5L, true);
//        // 사용자가 쓰려는 리뷰의 리뷰 영수증 id
//        Long orderGroup = mockRequestDto.getOrderGroup();
//        // 리뷰 영수증 id 로 조회한 상세정보 : 사용되는 정보[영수증주인 user, 가게정보 store]
//        Orders mockOrders = Orders.builder()
//                .orderId(1L)    // 주문영수증 id
//                .user(mockUser)
//                .store()
//                .build();
//        when(orderRepository.findTopByOrderGroup(orderGroup)).thenReturn();
//
//        // when
//        //응답 = reviewService(a, b);
//
//        // then
//        assertThat(1).isEqualTo(1);
//    }
//}