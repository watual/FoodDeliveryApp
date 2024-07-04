package com.sparta.fooddeliveryapp.domain.like.service;

import com.sparta.fooddeliveryapp.domain.like.dto.UserLikeRequestDto;
import com.sparta.fooddeliveryapp.domain.like.dto.UserLikeResponseDto;
import com.sparta.fooddeliveryapp.domain.like.entity.UserLike;
import com.sparta.fooddeliveryapp.domain.like.entity.UserLikeType;
import com.sparta.fooddeliveryapp.domain.like.repository.UserLikeRepository;
import com.sparta.fooddeliveryapp.domain.review.entity.Review;
import com.sparta.fooddeliveryapp.domain.review.repository.querydsl.ReviewRepositoryCustom;
import com.sparta.fooddeliveryapp.domain.store.entity.Store;
import com.sparta.fooddeliveryapp.domain.store.repository.querydsl.StoreRepositoryCustom;
import com.sparta.fooddeliveryapp.domain.user.entity.User;
import com.sparta.fooddeliveryapp.global.error.exception.DuplicateLikeException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserLikeService {
    private final UserLikeRepository userLikeRepository;
    private final ReviewRepositoryCustom reviewRepositoryCustom;
    private final StoreRepositoryCustom storeRepositoryCustom;

    @Transactional
    // 좋아요 남기기
    public UserLike addUserLike(User user, UserLikeRequestDto userLikeRequestDto) {
        // 사용자당 한 번만 좋아요 가능
        if (userLikeRepository.existsByUserAndUserLikeTypeAndTypeId(user, userLikeRequestDto.getUserLikeType(), userLikeRequestDto.getTypeId())) {
            throw new DuplicateLikeException("이미 좋아요를 눌렀습니다");
        }
        // 자신의 게시물에는 좋아요 등록 불가
        // -> getUserLikeType, getTypeId 로 가져온 정보의 주인이 user 와 같으면 등록 불가능
        if (userLikeRequestDto.getUserLikeType().equals(UserLikeType.REVIEW)) {
            // 리뷰 주인 누군지 가져오기
            Review review = reviewRepositoryCustom.selectfromReviewWhereReviewId(userLikeRequestDto.getTypeId());
            if (review.getUser().getUserId().equals(user.getUserId())) {
                throw new IllegalArgumentException("본인 리뷰에는 좋아요를 누를 수 없습니다");
            }
            // 리뷰의 좋아요 개수 증가
            review.addUserLike();
        } else if (userLikeRequestDto.getUserLikeType().equals(UserLikeType.STORE)) {
            // 가게 주인 누군지 가져오기
            Store store = storeRepositoryCustom.selectfromStoreWhereStoreId(userLikeRequestDto.getTypeId());
            if (store.getUser().getUserId().equals(user.getUserId())) {
                throw new IllegalArgumentException("본인 매장에는 좋아요를 누를 수 없습니다");
            }
            // 가게의 좋아요 개수 증가
            store.addUserLike();
        } else {
            throw new IllegalArgumentException("좋아요를 달 수 없는 입니다");
        }

        return userLikeRepository.save(
                UserLike.builder()
                        .user(user)
                        .userLikeType(userLikeRequestDto.getUserLikeType())
                        .typeId(userLikeRequestDto.getTypeId())
                        .build());
    }

    // 좋아요 취소
    public void deleteUserLike(User user, UserLikeRequestDto userLikeRequestDto) {
        // 사용자 좋아요 취소 _ 데이터 좋아요 상태인지 확인, 본인확인,
        UserLike userLike = userLikeRepository.findByUserAndUserLikeTypeAndTypeId(user, userLikeRequestDto.getUserLikeType(), userLikeRequestDto.getTypeId()).orElseThrow(
                () -> new NullPointerException("취소할 좋아요가 없습니다")
        );
        userLikeRepository.delete(userLike);
    }

    public List<UserLikeResponseDto> getUserLike(UserLikeRequestDto userLikeRequestDto) {
        List<UserLike> userLikeList = userLikeRepository.findAllByUserLikeTypeAndTypeId(userLikeRequestDto.getUserLikeType(), userLikeRequestDto.getTypeId()).orElseThrow(
                () -> new NullPointerException("등록된 좋아요가 없습니다")
        );
        return userLikeList.stream().map(
                userLike -> UserLikeResponseDto.builder()
                        .userId(userLike.getUser().getUserId())
                        .userLikeType(userLike.getUserLikeType())
                        .typeId(userLike.getTypeId())
                        .build()
        ).toList();
    }
}
