package com.sparta.fooddeliveryapp.domain.order.service;

import com.sparta.fooddeliveryapp.domain.menu.entity.Menu;
import com.sparta.fooddeliveryapp.domain.menu.repository.MenuRepository;
import com.sparta.fooddeliveryapp.domain.order.dto.OrderDetailRequestDto;
import com.sparta.fooddeliveryapp.domain.order.dto.OrderRequestDto;
import com.sparta.fooddeliveryapp.domain.order.entity.Order;
import com.sparta.fooddeliveryapp.domain.order.entity.OrderDetail;
import com.sparta.fooddeliveryapp.domain.order.repository.OrderDetailRepository;
import com.sparta.fooddeliveryapp.domain.order.repository.OrderRepository;
import com.sparta.fooddeliveryapp.domain.store.entity.Store;
import com.sparta.fooddeliveryapp.domain.user.entity.User;
import com.sparta.fooddeliveryapp.domain.user.repository.UserRepository;
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
    public void order(Long userId, OrderRequestDto orderRequserDto) {
        //유저 조회
        User user = userRepository.findById(userId).get();

        // 주문메뉴 리스트
        List<OrderDetailRequestDto> orderDetailDtoList = orderRequserDto.getOrderDetailDtoList();
        Store store = null;
        if (!orderDetailDtoList.isEmpty()) {
            Long menuId = orderDetailDtoList.get(0).getMenuId();
            store = menuRepository.findById(menuId).get().getStore();
        }

        Order order = new Order(store, user, orderRequserDto.getTotalPrice());
        //주문 생성
        orderRepository.save(order);

        //주문 상세
        for (OrderDetailRequestDto dto : orderDetailDtoList) {
            Menu menu = menuRepository.findById(dto.getMenuId()).get();
            OrderDetail orderDetail = new OrderDetail(order, menu, dto.getCount());
            orderDetailRepository.save(orderDetail);
        }
    }
}
