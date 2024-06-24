package com.sparta.fooddeliveryapp.domain.follow.service;

import com.sparta.fooddeliveryapp.domain.follow.dto.FollowRequestDto;
import com.sparta.fooddeliveryapp.domain.follow.entity.Follow;
import com.sparta.fooddeliveryapp.domain.follow.repository.FollowRepository;
import com.sparta.fooddeliveryapp.domain.like.entity.UserLike;
import com.sparta.fooddeliveryapp.domain.store.entity.Store;
import com.sparta.fooddeliveryapp.domain.store.repository.StoreRepository;
import com.sparta.fooddeliveryapp.domain.user.entity.User;
import com.sparta.fooddeliveryapp.global.error.exception.DuplicateLikeException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final StoreRepository storeRepository;

    public Follow addUserLike(User user, FollowRequestDto followRequestDto) {
        Store store = storeRepository.findById(followRequestDto.getStoreId()).orElseThrow(
                () -> new NullPointerException("없는 매장입니다")
        );

        if(followRepository.existsByUserAndStore(user, store)){
            throw new DuplicateLikeException("이미 팔로우 중 입니다");
        }
        return followRepository.save(
                Follow.builder()
                        .store(store)
                        .user(user)
                        .build());
    }
}
