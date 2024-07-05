package com.sparta.fooddeliveryapp.domain.review.repository.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.fooddeliveryapp.domain.like.entity.QUserLike;
import com.sparta.fooddeliveryapp.domain.like.entity.UserLikeType;
import com.sparta.fooddeliveryapp.domain.review.entity.QReview;
import com.sparta.fooddeliveryapp.domain.review.entity.Review;
import com.sparta.fooddeliveryapp.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Slf4j
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

    @Override
    public Page<Review> selectfromStoreWhereUserLike(User user, Pageable pageable) {
        // userLike 에서 좋아요를 WHERE user_id로 찾고 찾은 review_id 들을 review repo 에서 찾아 List 로 반환
        QUserLike qUserLike = QUserLike.userLike;
        QReview qReview = QReview.review;
        List<Review> reviewList = jpaQueryFactory.select(qReview)
                .from(qReview)
                .innerJoin(qUserLike).on(qReview.reviewId.eq(qUserLike.typeId))
                .where(qUserLike.userLikeType.eq(UserLikeType.STORE)
                        .and(qUserLike.user.eq(user)))
                .orderBy(qUserLike.createdAt.desc())  // created_at을 기준으로 내림차순 정렬
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = Optional.ofNullable(
                jpaQueryFactory.select(qReview.count())
                        .from(qReview)
                        .innerJoin(qUserLike).on(qReview.reviewId.eq(qUserLike.typeId))
                        .where(qUserLike.userLikeType.eq(UserLikeType.STORE)
                                .and(qUserLike.user.eq(user)))
                        .fetchOne()
        ).orElse(0L);

        return new PageImpl<>(reviewList, pageable, total);
    }
}
