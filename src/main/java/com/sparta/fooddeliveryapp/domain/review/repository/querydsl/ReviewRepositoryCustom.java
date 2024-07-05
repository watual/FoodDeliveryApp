package com.sparta.fooddeliveryapp.domain.review.repository.querydsl;

import com.sparta.fooddeliveryapp.domain.review.entity.Review;
import com.sparta.fooddeliveryapp.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public interface ReviewRepositoryCustom {

    Review selectfromReviewWhereReviewId(Long typeId);

    Page<Review> selectfromStoreWhereUserLike(User user, Pageable pageable);

}
