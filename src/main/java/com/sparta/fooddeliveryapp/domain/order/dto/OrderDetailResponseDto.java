package com.sparta.fooddeliveryapp.domain.order.dto;

import com.sparta.fooddeliveryapp.domain.order.entity.OrderDetail;
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
public class OrderDetailResponseDto {

    private Long orderDetailId;

    private String menuName;

    private Integer count;

    private Long ordersId;

    public OrderDetailResponseDto(OrderDetail orderDetail) {
        this.orderDetailId = orderDetail.getOrderDetailId();
        this.menuName = orderDetail.getMenuName();
        this.count = orderDetail.getCount();
        this.ordersId = orderDetail.getOrders().getOrdersId();
    }
}
