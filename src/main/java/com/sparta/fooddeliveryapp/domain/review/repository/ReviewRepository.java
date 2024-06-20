package com.sparta.fooddeliveryapp.domain.review.repository;

import com.sparta.fooddeliveryapp.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
