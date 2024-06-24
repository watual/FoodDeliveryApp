package com.sparta.fooddeliveryapp.domain.order.repository;

import com.sparta.fooddeliveryapp.domain.order.entity.OrderDetail;
import java.util.List;

import com.sparta.fooddeliveryapp.domain.order.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    // BUGFIX : orderId -> ordersId
    List<OrderDetail> findAllByOrders(Orders orders);
}
