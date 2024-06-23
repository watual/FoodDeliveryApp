package com.sparta.fooddeliveryapp.domain.follow.controller;

import com.sparta.fooddeliveryapp.domain.follow.dto.FollowRequestDto;
import com.sparta.fooddeliveryapp.domain.follow.entity.Follow;
import com.sparta.fooddeliveryapp.domain.follow.service.FollowService;
import com.sparta.fooddeliveryapp.domain.user.entity.User;
import com.sparta.fooddeliveryapp.domain.user.repository.UserRepository;
import com.sparta.fooddeliveryapp.global.common.ResponseDto;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/follow")
public class FollowController {

    private final FollowService followService;

    private final UserRepository userRepository;
    private User getTempUser() {
        return userRepository.findById(1L).orElse(null);
    }

    @PostMapping
    public ResponseEntity<ResponseDto> addFollow(
//            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody FollowRequestDto followRequestDto
    ) {
        Follow follow = followService.addUserLike(getTempUser(), followRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseDto.builder()
                        .status(HttpStatus.OK)
                        .message("좋아요 완료")
                        .build());
    }
}
