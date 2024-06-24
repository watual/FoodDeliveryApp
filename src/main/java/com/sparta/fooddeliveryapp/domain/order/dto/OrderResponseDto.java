package com.sparta.fooddeliveryapp.domain.order.dto;

import com.sparta.fooddeliveryapp.domain.order.entity.Orders;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class OrderResponseDto {

    private Long orderId;

    private String storeName;

    private Integer totalPrice;

    private List<OrderDetailResponseDto> orderDetailResponseDtoList;

    private LocalDateTime createdAt;

    public OrderResponseDto(Orders orders, List<OrderDetailResponseDto> dtoList) {
        // BUGFIX : OrderId -> OrdersId 리팩토링 미반영 수정
        this.orderId = orders.getOrdersId();
        this.storeName = orders.getStoreName();
        this.totalPrice = orders.getTotalPrice();
        this.createdAt = orders.getCreatedAt();
        this.orderDetailResponseDtoList = dtoList;
    }

}
