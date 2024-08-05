package com.fourdays.foodage.review.domain;

import static com.fourdays.foodage.member.domain.QMember.*;
import static com.fourdays.foodage.review.domain.QReview.*;
import static com.fourdays.foodage.review.domain.QReviewImage.*;
import static com.fourdays.foodage.tag.domain.QReviewTag.*;
import static com.querydsl.core.group.GroupBy.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.fourdays.foodage.member.vo.MemberId;
import com.fourdays.foodage.review.domain.model.ReviewImageModel;
import com.fourdays.foodage.review.domain.model.ReviewModel;
import com.fourdays.foodage.review.domain.model.ReviewModelWithThumbnail;
import com.fourdays.foodage.review.dto.PeriodReviewResponse;
import com.fourdays.foodage.review.dto.RecentReviewResponse;
import com.fourdays.foodage.tag.domain.model.TagModel;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
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
	public ReviewModel findReviewById(MemberId memberId, Long reviewId) {

		ReviewModel reviewModel = query
			.select(
				review.id,
				review.restaurant,
				review.contents,
				review.rating,
				review.createdAt,
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
				reviewIdEq(reviewId)
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
							review.createdAt,
							list(Projections.constructor(
									TagModel.class,
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
			).get(0);

		return reviewModel;
	}

	@Override
	public List<ReviewModelWithThumbnail> findReviewsByDate(MemberId memberId, LocalDate date) {

		List<ReviewModelWithThumbnail> reviewModel = query
			.select(
				review.id,
				review.restaurant,
				review.contents,
				review.rating,
				review.createdAt,
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
							ReviewModelWithThumbnail.class,
							review.id,
							review.restaurant,
							review.contents,
							review.rating,
							review.createdAt,
							list(Projections.constructor(
									TagModel.class,
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
	public List<PeriodReviewResponse> findReviewsByPeriod(MemberId memberId,
		LocalDateTime startDate, LocalDateTime endDate) {

		List<PeriodReviewResponse> reviewModel = query
			.select(Projections.constructor(
					PeriodReviewResponse.class,
					review.id,
					review.createdAt
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
	public List<ReviewModelWithThumbnail> findReviews(List<Long> ids, MemberId memberId, Pageable pageable) {

		List<ReviewModelWithThumbnail> reviewModel = query
			.select(
				review.id,
				review.restaurant,
				review.contents,
				review.rating,
				review.createdAt,
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
				reviewIdIn(ids)
			)
			.orderBy(
				sort(pageable.getSort())
			)
			.transform(
				groupBy(review.id)
					.list(
						Projections.constructor(
							ReviewModelWithThumbnail.class,
							review.id,
							review.restaurant,
							review.contents,
							review.rating,
							review.createdAt,
							list(Projections.constructor(
									TagModel.class,
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

	@Override
	public Slice<Long> findReviewIds(Long idx, MemberId memberId, Pageable pageable) {

		List<Long> reviewModel = query
			.select(review.id)
			.from(review)
			.innerJoin(member).on(review.creatorId.eq(member.id))
			.innerJoin(reviewTag).on(review.id.eq(reviewTag.reviewId))
			.leftJoin(reviewImage).on(
				review.id.eq(reviewImage.reviewId)
			)
			.where(
				memberIdEq(memberId),
				reviewIdLt(idx)
			)
			.groupBy(review.id)
			.orderBy(
				sort(pageable.getSort())
			)
			.limit(pageable.getPageSize() + 1)
			.fetch();

		return checkLastPage(pageable, reviewModel);
	}

	@Override
	public int countByReviewId(MemberId memberId) {

		int totalCount = query
			.select(review.id)
			.from(review)
			.innerJoin(member).on(review.creatorId.eq(member.id))
			.innerJoin(reviewTag).on(review.id.eq(reviewTag.reviewId))
			.where(
				memberIdEq(memberId)
			)
			.groupBy(review.id)
			.fetch().size();

		return totalCount;
	}

	//////////////////////////////////////////////////////////////////

	private BooleanExpression memberIdEq(MemberId memberId) {

		return member.accountEmail.eq(memberId.accountEmail())
			.and(member.oauthId.oauthServerType.eq(memberId.oauthServerType()));
	}

	private BooleanExpression reviewIdEq(Long reviewId) {

		return reviewId != null
			? review.id.eq(reviewId)
			: null;
	}

	private BooleanExpression reviewIdLt(Long reviewId) {

		return reviewId != null
			? review.id.lt(reviewId)
			: null;
	}

	private BooleanExpression reviewIdIn(List<Long> reviewId) {

		return reviewId != null
			? review.id.in(reviewId)
			: null;
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

	// pagination
	private <T> Slice<T> checkLastPage(Pageable pageable, List<T> results) {

		boolean hasNext = false;

		// 조회된 개수(ex. 10) > 요청 페이지 사이즈(ex. 5)면 뒤에 조회될 페이지가 남아 있다는 것
		if (results.size() > pageable.getPageSize()) {
			hasNext = true;
			results.remove(pageable.getPageSize());
		}

		return new SliceImpl<>(results, pageable, hasNext);
	}

	private OrderSpecifier[] sort(Sort sort) {

		List<OrderSpecifier> result = new ArrayList<>();

		if (sort.isSorted()) {
			if (sort.getOrderFor("rating").isDescending()) {
				result.add(new OrderSpecifier(Order.DESC, review.rating));
			} else {
				result.add(new OrderSpecifier(Order.ASC, review.rating));
			}

			if (sort.getOrderFor("created_at").isDescending()) {
				result.add(new OrderSpecifier(Order.DESC, review.createdAt));
			} else {
				result.add(new OrderSpecifier(Order.ASC, review.createdAt));
			}
		}
		// default
		result.add(new OrderSpecifier(Order.ASC, reviewTag.tagId)); // 먼저 등록된 순
		result.add(new OrderSpecifier(Order.ASC, reviewImage.sequence)); // 먼저 등록된 순
		result.add(new OrderSpecifier(Order.DESC, review.id));

		return result.toArray(new OrderSpecifier[result.size()]);
	}
}
