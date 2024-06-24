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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
        User user = userRepository.findById(userId).orElseThrow();
        // 주문메뉴 리스트
        List<OrderDetailRequestDto> orderDetailDtoList = orderRequestDto.getOrderDetailDtoList();
        // 비어있으면 "담은 메뉴가 없습니다!" 오류를 던짐, 있으면 정상로직을 수행
        if (orderDetailDtoList.isEmpty()) {
            throw new NullPointerException("장바구니가 비어있습니다.");
        }   //없으면 로직 수행 안하고 계속~
        // 없으면, store = null, orderDetailDtoList = null, orders 생성, save

        Long menuId = orderDetailDtoList.get(0).getMenuId();
        Store store = menuRepository.findById(menuId).orElseThrow().getStore();

        Orders orders = new Orders(store, user, orderRequestDto.getTotalPrice());
        //주문 생성
        orderRepository.save(orders);

        //주문 상세
        for (OrderDetailRequestDto dto : orderDetailDtoList) {
            Menu menu = menuRepository.findById(dto.getMenuId()).orElseThrow();
            OrderDetail orderDetail = new OrderDetail(orders, menu, dto.getCount());
            orderDetailRepository.save(orderDetail);
        }
    }

    public List<OrderResponseDto> getOrderList(Long userId, int page, int size) {

        User user = userRepository.findById(userId).orElseThrow();
        Pageable pageable = PageRequest.of(page, size);
        Page<Orders> ordersPage = orderRepository.findAllByUserOrderByCreatedAtDesc(user, pageable);

        List<OrderResponseDto> orderResponseDtoList = new ArrayList<>();
        List<OrderDetailResponseDto> orderDetailResponseDtoList = new ArrayList<>();

        for (Orders orders : ordersPage) {

            //이 주문번호에 해당하는 주문상세 리스트 조회하는 메서드가 필요하다.
            List<OrderDetail> orderDetailList = orderDetailRepository.findAllByOrderId(orders.getOrdersId());

            for (OrderDetail orderDetail : orderDetailList) {
                orderDetailResponseDtoList.add(new OrderDetailResponseDto(orderDetail));
            }
            orderResponseDtoList.add(new OrderResponseDto(orders, orderDetailResponseDtoList));
        }

        return orderResponseDtoList;

    }
}
