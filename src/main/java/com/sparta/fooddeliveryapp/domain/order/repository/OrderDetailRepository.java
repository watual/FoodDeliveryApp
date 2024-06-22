package com.sparta.fooddeliveryapp.domain.order.repository;

import com.sparta.fooddeliveryapp.domain.order.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

}
