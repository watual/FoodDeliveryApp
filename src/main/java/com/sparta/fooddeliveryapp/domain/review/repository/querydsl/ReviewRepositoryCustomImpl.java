package com.sparta.fooddeliveryapp.domain.review.repository.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.fooddeliveryapp.domain.review.entity.QReview;
import com.sparta.fooddeliveryapp.domain.review.entity.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ReviewRepositoryCustomImpl implements ReviewRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Review selectfromReviewWhereReviewId(Long typeId) {
        QReview qReview = QReview.review;
        Review review = jpaQueryFactory.selectFrom(qReview)
                .where(qReview.reviewId.eq(typeId))
                .fetchOne();
        return review;
    }
}
