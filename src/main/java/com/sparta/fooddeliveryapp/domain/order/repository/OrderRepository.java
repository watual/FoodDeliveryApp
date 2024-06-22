package com.sparta.fooddeliveryapp.domain.order.repository;

import com.sparta.fooddeliveryapp.domain.order.entity.Orders;
import com.sparta.fooddeliveryapp.domain.user.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {

    List<Orders> findAllByUserOrderByCreatedAtDesc(User user);

}
