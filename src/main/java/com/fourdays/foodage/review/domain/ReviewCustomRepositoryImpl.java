package com.fourdays.foodage.review.domain;

import static com.fourdays.foodage.member.domain.QMember.*;
import static com.fourdays.foodage.review.domain.QReview.*;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.fourdays.foodage.home.dto.WeeklyReviewResponse;
import com.fourdays.foodage.member.vo.MemberId;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class ReviewCustomRepositoryImpl implements ReviewCustomRepository {

	private final JPAQueryFactory query;

	public ReviewCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
		this.query = jpaQueryFactory;
	}

	@Override
	public List<WeeklyReviewResponse> findWeeklyReview(MemberId memberId,
		LocalDateTime startDate, LocalDateTime endDate) {

		List<WeeklyReviewResponse> reviewModel = query
			.select(Projections.constructor(
					WeeklyReviewResponse.class,
					review.id,
					review.createdAt,
					review.lastEatenFood
				)
			)
			.from(review)
			.innerJoin(member).on(review.createdByMemberId.eq(member.id))
			.where(
				memberIdEq(memberId),
				dateFilter(startDate, endDate)
			)
			.orderBy(review.createdAt.asc())
			.fetch();

		return reviewModel;
	}

	private BooleanExpression memberIdEq(MemberId memberId) {

		return member.accountEmail.eq(memberId.accountEmail())
			.and(member.oauthId.oauthServerType.eq(memberId.oauthServerType()));
	}

	private BooleanExpression dateFilter(LocalDateTime startDate, LocalDateTime endDate) {

		if (!isValidDateRange(startDate, endDate)) {
			return null;
		}
		return review.createdAt
			.between(startDate, endDate);
	}

	private boolean isValidDateRange(LocalDateTime startDate, LocalDateTime endDate) {

		if (startDate == null || endDate == null) {
			return false;
		}
		return startDate.isBefore(endDate); // ex. 05-23 ~ 05-30
	}
}
