package com.fourdays.foodage.member.domain;

import static com.fourdays.foodage.member.domain.QMember.*;
import static com.fourdays.foodage.review.domain.QReview.*;
import static com.fourdays.foodage.tag.domain.QReviewTag.*;

import org.springframework.stereotype.Repository;

import com.fourdays.foodage.member.vo.MemberId;
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
			.from(review)
			.innerJoin(member).on(review.creatorId.eq(member.id))
			.innerJoin(reviewTag).on(review.id.eq(reviewTag.reviewId))
			.where(
				memberIdEq(memberId)
			).fetch().size();
	}

	//////////////////////////////////////////////////////////////////

	private BooleanExpression memberIdEq(MemberId memberId) {

		return member.accountEmail.eq(memberId.accountEmail())
			.and(member.oauthId.oauthServerType.eq(memberId.oauthServerType()));
	}
}
