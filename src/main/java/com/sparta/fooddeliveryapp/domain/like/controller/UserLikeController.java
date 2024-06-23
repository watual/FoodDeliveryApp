package com.sparta.fooddeliveryapp.domain.like.controller;

import com.sparta.fooddeliveryapp.domain.like.dto.UserLikeRequestDto;
import com.sparta.fooddeliveryapp.domain.like.entity.UserLike;
import com.sparta.fooddeliveryapp.domain.like.service.UserLikeService;
import com.sparta.fooddeliveryapp.domain.user.entity.User;
import com.sparta.fooddeliveryapp.domain.user.repository.UserRepository;
import com.sparta.fooddeliveryapp.global.common.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/like")
public class UserLikeController {

    private final UserLikeService userLikeService;

    // 임시 user 생성
    private final UserRepository userRepository;
    private User getTempUser() {
        return userRepository.findById(1L).orElse(null);
    }

    @PostMapping
    public ResponseEntity<ResponseDto> addLike(@RequestBody UserLikeRequestDto userLikeRequestDto) {
        UserLike userLike = userLikeService.addUserLike(getTempUser(), userLikeRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseDto.builder()
                        .status(HttpStatus.OK)
                        .message("Success")
                        .data(userLike)
                        .build());
    }
}
