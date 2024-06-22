package com.sparta.fooddeliveryapp.domain.order.repository;

import com.sparta.fooddeliveryapp.domain.order.entity.OrderDetail;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

    @Query("select od from OrderDetail od where od.order.orderId = :orderId")
    List<OrderDetail> findAllByOrderId(Long orderId);
}