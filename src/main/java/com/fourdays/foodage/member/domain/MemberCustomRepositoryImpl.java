package com.fourdays.foodage.member.domain;

import static com.fourdays.foodage.member.domain.QMember.*;
import static com.fourdays.foodage.review.domain.QReview.*;
import static com.fourdays.foodage.review.domain.QReviewTag.*;

import org.springframework.stereotype.Repository;

import com.fourdays.foodage.member.vo.MemberId;
import com.fourdays.foodage.tag.domain.TagCategory;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class MemberCustomRepositoryImpl implements MemberCustomRepository {

	private final JPAQueryFactory query;

	public MemberCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
		this.query = jpaQueryFactory;
	}

	@Override
	public int findReviewCountByMemberId(MemberId memberId) {

		return query
			.select(
				review.id
			)
			.from(member)
			.innerJoin(review).on(member.id.eq(review.createdBy))
			.where(
				memberIdEq(memberId)
			).fetch().size();
	}

	@Override
	public int findTagCountByMemberId(MemberId memberId, TagCategory tagCategory) {

		return query
			.select(
				reviewTag.id
			)
			.from(review)
			.innerJoin(member).on(review.createdBy.eq(member.id))
			.innerJoin(reviewTag).on(review.id.eq(reviewTag.reviewId))
			.where(
				memberIdEq(memberId),
				reviewTag.tagName.eq(tagCategory.getDbFieldValue())
			).fetch().size();
	}

	//////////////////////////////////////////////////////////////////

	private BooleanExpression memberIdEq(MemberId memberId) {

		return member.accountEmail.eq(memberId.accountEmail())
			.and(member.oauthId.oauthServerType.eq(memberId.oauthServerType()));
	}
}
