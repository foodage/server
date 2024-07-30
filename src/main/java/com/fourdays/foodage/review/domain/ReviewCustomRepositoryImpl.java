package com.fourdays.foodage.review.domain;

import static com.fourdays.foodage.member.domain.QMember.*;
import static com.fourdays.foodage.review.domain.QReview.*;
import static com.fourdays.foodage.review.domain.QReviewImage.*;
import static com.fourdays.foodage.tag.domain.QReviewTag.*;
import static com.querydsl.core.group.GroupBy.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.fourdays.foodage.member.vo.MemberId;
import com.fourdays.foodage.review.domain.model.ReviewImageModel;
import com.fourdays.foodage.review.domain.model.ReviewModel;
import com.fourdays.foodage.review.dto.PeriodReviewResponse;
import com.fourdays.foodage.review.dto.RecentReviewResponse;
import com.fourdays.foodage.tag.dto.TagInfo;
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
	public List<PeriodReviewResponse> findReviewsByPeriod(MemberId memberId,
		LocalDateTime startDate, LocalDateTime endDate) {

		List<PeriodReviewResponse> reviewModel = query
			.select(Projections.constructor(
					PeriodReviewResponse.class,
					review.id,
					review.createdAt,
					review.lastEatenFood
				)
			)
			.from(review)
			.innerJoin(member).on(review.creatorId.eq(member.id))
			.where(
				memberIdEq(memberId),
				dateFilter(startDate, endDate)
			)
			.orderBy(review.createdAt.asc())
			.fetch();

		return reviewModel;
	}

	@Override
	public List<ReviewModel> findReviewsByDate(MemberId memberId, LocalDate date) {

		List<ReviewModel> reviewModel = query
			.select(
				review.id,
				review.restaurant,
				review.contents,
				review.rating,
				reviewTag.tagId,
				reviewTag.tagName,
				reviewTag.tagBgColor,
				reviewTag.tagTextColor,
				reviewImage.sequence,
				reviewImage.imageUrl,
				reviewImage.useThumbnail
			)
			.from(review)
			.innerJoin(member).on(review.creatorId.eq(member.id))
			.innerJoin(reviewTag).on(review.id.eq(reviewTag.reviewId))
			.leftJoin(reviewImage).on(
				review.id.eq(reviewImage.reviewId)
			)
			.where(
				memberIdEq(memberId),
				createdAtEq(date)
			)
			.orderBy(
				review.id.desc(), // 최신순 정렬
				reviewTag.tagId.asc(),
				reviewImage.sequence.asc() // 먼저 등록된 순으로 정렬
			)
			.transform(
				groupBy(review.id)
					.list(
						Projections.constructor(
							ReviewModel.class,
							review.id,
							review.restaurant,
							review.contents,
							review.rating,
							list(Projections.constructor(
									TagInfo.class,
									reviewTag.id,
									reviewTag.tagName,
									reviewTag.tagBgColor,
									reviewTag.tagTextColor
								).skipNulls()
							),
							list(Projections.constructor(
									ReviewImageModel.class,
									reviewImage.id,
									reviewImage.sequence,
									reviewImage.imageUrl,
									reviewImage.useThumbnail
								).skipNulls()
							)
						)
					)
			);

		return reviewModel;
	}

	@Override
	public List<RecentReviewResponse> findRecentReviews(MemberId memberId, int limit) {

		List<RecentReviewResponse> reviewModel = query
			.select(Projections.constructor(
					RecentReviewResponse.class,
					review.id,
					review.restaurant,
					review.address,
					reviewImage.imageUrl,
					review.createdAt
				)
			)
			.from(review)
			.innerJoin(member).on(review.creatorId.eq(member.id))
			.innerJoin(reviewImage).on(
				review.id.eq(reviewImage.reviewId),
				review.thumbnailId.eq(reviewImage.id)
			)
			.where(
				memberIdEq(memberId)
			)
			.orderBy(review.createdAt.desc()) // 최신순 정렬
			.limit(limit)
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

	private BooleanExpression createdAtEq(LocalDate date) {

		return review.createdAt
			.between(date.atStartOfDay(), date.atTime(LocalTime.MAX));
	}
}
