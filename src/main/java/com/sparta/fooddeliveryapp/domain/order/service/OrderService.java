package com.sparta.fooddeliveryapp.domain.order.service;

import com.sparta.fooddeliveryapp.domain.menu.entity.Menu;
import com.sparta.fooddeliveryapp.domain.menu.repository.MenuRepository;
import com.sparta.fooddeliveryapp.domain.order.dto.OrderDetailRequestDto;
import com.sparta.fooddeliveryapp.domain.order.dto.OrderDetailResponseDto;
import com.sparta.fooddeliveryapp.domain.order.dto.OrderRequestDto;
import com.sparta.fooddeliveryapp.domain.order.dto.OrderResponseDto;
import com.sparta.fooddeliveryapp.domain.order.entity.OrderDetail;
import com.sparta.fooddeliveryapp.domain.order.entity.Orders;
import com.sparta.fooddeliveryapp.domain.order.repository.OrderDetailRepository;
import com.sparta.fooddeliveryapp.domain.order.repository.OrderRepository;
import com.sparta.fooddeliveryapp.domain.store.entity.Store;
import com.sparta.fooddeliveryapp.domain.user.entity.User;
import com.sparta.fooddeliveryapp.domain.user.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final OrderDetailRepository orderDetailRepository;

    private final UserRepository userRepository;

    private final MenuRepository menuRepository;

    @Transactional
    public void order(Long userId, OrderRequestDto orderRequestDto) {
        //유저 조회
        User user = userRepository.findById(userId).get();

        // 주문메뉴 리스트
        List<OrderDetailRequestDto> orderDetailDtoList = orderRequestDto.getOrderDetailDtoList();
        Store store = null;
        if (!orderDetailDtoList.isEmpty()) {
            Long menuId = orderDetailDtoList.get(0).getMenuId();
            store = menuRepository.findById(menuId).get().getStoreId();
        }

        Orders orders = new Orders(store, user, orderRequestDto.getTotalPrice());
        //주문 생성
        orderRepository.save(orders);

        //주문 상세
        for (OrderDetailRequestDto dto : orderDetailDtoList) {
            Menu menu = menuRepository.findById(dto.getMenuId()).get();
            OrderDetail orderDetail = new OrderDetail(orders, menu, dto.getCount());
            orderDetailRepository.save(orderDetail);
        }
    }

    public List<OrderResponseDto> getOrderList(Long userId) {

        User user = userRepository.findById(userId).get();
        List<Orders> ordersList = orderRepository.findAllByUserOrderByCreatedAtDesc(user);
        List<OrderResponseDto> orderResponseDtoList = new ArrayList<>();
        List<OrderDetailResponseDto> orderDetailResponseDtoList = new ArrayList<>();

        for (Orders orders : ordersList) {

            //이 주문번호에 해당하는 주문상세 리스트 조회하는 메서드가 필요하다.
            List<OrderDetail> orderDetailList = orderDetailRepository.findAllByOrderId(orders.getOrderId());

            for (OrderDetail orderDetail : orderDetailList) {

                orderDetailResponseDtoList.add(new OrderDetailResponseDto(
                    orderDetail.getOrderDetailId(),
                    orderDetail.getMenu().getMenuName(),
                    orderDetail.getCount()
                    )
                );
            }

            orderResponseDtoList.add(new OrderResponseDto(
                orders.getOrderId(),
                orders.getStore().getStoreName(),
                orders.getTotalPrice(),
                orderDetailResponseDtoList,
                orders.getCreatedAt()
                )
            );
        }

        return orderResponseDtoList;

    }
}
