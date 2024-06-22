package com.sparta.fooddeliveryapp.domain.order.dto;

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
public class OrderDetailRequestDto {

    private Long menuId;

    private Integer count;
}
