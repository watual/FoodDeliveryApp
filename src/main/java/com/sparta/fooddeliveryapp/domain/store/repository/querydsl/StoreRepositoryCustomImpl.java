package com.sparta.fooddeliveryapp.domain.store.repository.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.fooddeliveryapp.domain.review.entity.QReview;
import com.sparta.fooddeliveryapp.domain.review.entity.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class StoreRepositoryCustomImpl implements StoreRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Review selectfromReviewWhereReviewId(Long typeId) {
        QReview qReview = QReview.review;
        Review review = jpaQueryFactory.selectFrom(qReview)
                .where(qReview.reviewId.eq(typeId))
                .fetchOne();
        return review;
    }

    //    @Override
//    public Page<UserLike> selectLikeList(UserLikeType userLikeType, Long typeId, Pageable pageable) {
//        QUserLike qUserLike = QUserLike.userLike;
//        List<UserLike> userLikeList = jpaQueryFactory.selectFrom(qUserLike)
//                .where(qUserLike.userLikeType.eq(userLikeType)
//                        .and(qUserLike.typeId.eq(typeId)))
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize())
//                .fetch();
//
//        JPAQuery<Long> totalSize = jpaQueryFactory.select(Wildcard.count)
//                .from(qUserLike)
//                .where(qUserLike.userLikeType.eq(userLikeType)
//                        .and(qUserLike.typeId.eq(typeId)));
////                .where(qUserLike.typeId.eq(typeId));
//
//        return PageableExecutionUtils.getPage(userLikeList, pageable, totalSize::fetchOne);
//    }
}
