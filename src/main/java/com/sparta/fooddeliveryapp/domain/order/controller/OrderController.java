package com.sparta.fooddeliveryapp.domain.order.controller;

import com.sparta.fooddeliveryapp.domain.order.dto.OrderRequestDto;
import com.sparta.fooddeliveryapp.domain.order.service.OrderService;
import com.sparta.fooddeliveryapp.global.security.UserDetailsImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("api/order")
public class OrderController {

    public final OrderService orderService;

    @PostMapping
    public ResponseEntity order(@AuthenticationPrincipal UserDetailsImpl userDetails,
        @RequestBody OrderRequestDto orderRequserDto) {
        // 로그인한 사람의 ID
        Long userId = userDetails.getUser().getUserId();
        orderService.order(userId, orderRequserDto);

        return ResponseEntity.ok("주문 생성 완료");
    }

}
