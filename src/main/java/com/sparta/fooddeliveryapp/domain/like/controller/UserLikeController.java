package com.sparta.fooddeliveryapp.domain.like.controller;

import com.sparta.fooddeliveryapp.domain.like.dto.UserLikeRequestDto;
import com.sparta.fooddeliveryapp.domain.like.entity.UserLike;
import com.sparta.fooddeliveryapp.domain.like.repository.UserLikeRepository;
import com.sparta.fooddeliveryapp.domain.like.service.UserLikeService;
import com.sparta.fooddeliveryapp.domain.user.entity.User;
import com.sparta.fooddeliveryapp.domain.user.repository.UserRepository;
import com.sparta.fooddeliveryapp.global.common.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/like")
public class UserLikeController {

    private final UserLikeService userLikeService;

    // 임시 user 생성
    private final UserRepository userRepository;
    private final UserLikeRepository userLikeRepository;

    private User getTempUser() {
        return userRepository.findById(1L).orElse(null);
    }

    @PostMapping
    public ResponseEntity<ResponseDto> addUserLike(@RequestBody UserLikeRequestDto userLikeRequestDto) {
        UserLike userLike = userLikeService.addUserLike(getTempUser(), userLikeRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseDto.builder()
                        .status(HttpStatus.OK)
                        .message("좋아요 완료")
                        .build());
    }

    @DeleteMapping
    public ResponseEntity<ResponseDto> deleteUserLike(
//            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody UserLikeRequestDto userLikeRequestDto
    ) {
        userLikeService.deleteUserLike(getTempUser(), userLikeRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseDto.builder()
                        .status(HttpStatus.OK)
                        .message("좋아요 취소 완료")
                        .build());
    }

    @GetMapping
    public ResponseEntity<ResponseDto> getUserLike(@RequestBody UserLikeRequestDto userLikeRequestDto) {
        List<UserLike> userLikeList = userLikeService.getUserLike(userLikeRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseDto.builder()
                        .status(HttpStatus.OK)
                        .message("좋아요 조회 성공")
                        .data(userLikeList)
                        .build());
    }
}
