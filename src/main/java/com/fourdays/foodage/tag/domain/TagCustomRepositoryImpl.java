package com.fourdays.foodage.tag.domain;

import static com.fourdays.foodage.member.domain.QMember.*;
import static com.fourdays.foodage.review.domain.QReview.*;
import static com.fourdays.foodage.tag.domain.QReviewTag.*;
import static com.fourdays.foodage.tag.domain.QTag.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.fourdays.foodage.home.dto.TagUsageRankResponse;
import com.fourdays.foodage.member.vo.MemberId;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class TagCustomRepositoryImpl implements TagCustomRepository {

	private final JPAQueryFactory query;

	public TagCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
		this.query = jpaQueryFactory;
	}

	@Override
	public List<TagUsageRankResponse> findTagUsageRank(MemberId memberId) {

		List<TagUsageRankResponse> response = query
			.select(Projections.constructor(
					TagUsageRankResponse.class,
					tag.id,
					tag.name,
					reviewTag.tagId.count().as("usage_count")
				)
			)
			.from(review)
			.innerJoin(member).on(review.creatorId.eq(member.id))
			.leftJoin(reviewTag).on(review.id.eq(reviewTag.reviewId))
			.where(
				memberIdEq(memberId)
			)
			.groupBy(reviewTag.tagId)
			.fetch();

		return response;
	}

	private BooleanExpression memberIdEq(MemberId memberId) {

		return member.accountEmail.eq(memberId.accountEmail())
			.and(member.oauthId.oauthServerType.eq(memberId.oauthServerType()));
	}
}
