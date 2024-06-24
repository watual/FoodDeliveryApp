package com.sparta.fooddeliveryapp.domain.order.dto;

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
public class OrderRequestDto {

    private Integer totalPrice;

    private String store_id;

    private List<OrderDetailRequestDto> orderDetailDtoList;

}
