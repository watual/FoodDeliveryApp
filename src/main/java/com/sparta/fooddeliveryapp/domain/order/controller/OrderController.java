package com.sparta.fooddeliveryapp.domain.order.controller;

import com.sparta.fooddeliveryapp.domain.order.dto.OrderRequestDto;
import com.sparta.fooddeliveryapp.domain.order.dto.OrderResponseDto;
import com.sparta.fooddeliveryapp.domain.order.service.OrderService;
import com.sparta.fooddeliveryapp.global.security.UserDetailsImpl;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("api/orders")
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

    @GetMapping
    public ResponseEntity<List<OrderResponseDto>> getOrderList(@AuthenticationPrincipal UserDetailsImpl userDetails){

        List<OrderResponseDto> orderList = orderService.getOrderList(userDetails.getUser().getUserId());

        return ResponseEntity.status(HttpStatus.OK).body(orderList);

    }

}
