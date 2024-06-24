package com.sparta.fooddeliveryapp.domain.like.service;

import com.sparta.fooddeliveryapp.domain.like.dto.UserLikeRequestDto;
import com.sparta.fooddeliveryapp.domain.like.dto.UserLikeResponseDto;
import com.sparta.fooddeliveryapp.domain.like.entity.UserLike;
import com.sparta.fooddeliveryapp.domain.like.repository.UserLikeRepository;
import com.sparta.fooddeliveryapp.domain.user.entity.User;
import com.sparta.fooddeliveryapp.global.error.exception.DuplicateLikeException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserLikeService {
    private final UserLikeRepository userLikeRepository;

    public UserLike addUserLike(User user, UserLikeRequestDto userLikeRequestDto) {
        if(userLikeRepository.existsByUserAndUserLikeTypeAndTypeId(user, userLikeRequestDto.getUserLikeType(), userLikeRequestDto.getTypeId())){
            throw new DuplicateLikeException("이미 좋아요를 눌렀습니다");
        }
        return userLikeRepository.save(
                UserLike.builder()
                        .user(user)
                        .userLikeType(userLikeRequestDto.getUserLikeType())
                        .typeId(userLikeRequestDto.getTypeId())
                        .build());
    }

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
