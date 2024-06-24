package com.sparta.fooddeliveryapp.domain.follow.repository;

import com.sparta.fooddeliveryapp.domain.follow.entity.Follow;
import com.sparta.fooddeliveryapp.domain.store.entity.Store;
import com.sparta.fooddeliveryapp.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    boolean existsByUserAndStore(User user, Store store);

    Optional<Follow> findByUserAndStore(User user, Store store);

    List<Follow> findAllByUser(User user);

}
