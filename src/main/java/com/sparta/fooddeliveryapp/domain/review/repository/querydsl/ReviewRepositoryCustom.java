package com.sparta.fooddeliveryapp.domain.review.repository.querydsl;

import com.sparta.fooddeliveryapp.domain.review.entity.Review;

public interface ReviewRepositoryCustom {

    Review selectfromReviewWhereReviewId(Long typeId);

}
