package com.fourdays.foodage.inquiry.domain;

import static com.fourdays.foodage.inquiry.domain.QInquiry.*;
import static com.fourdays.foodage.member.domain.QMember.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.fourdays.foodage.member.vo.MemberId;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class InquiryCustomRepositoryImpl implements InquiryCustomRepository {

	private final JPAQueryFactory query;

	public InquiryCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
		this.query = jpaQueryFactory;
	}

	@Override
	public int countByInquiryId(final MemberId memberId) {

		return query
			.select(inquiry.id)
			.from(inquiry)
			.innerJoin(member).on(inquiry.createdBy.eq(member.id))
			.where(memberIdEq(memberId))
			.groupBy(inquiry.id)
			.fetch().size();
	}

	@Override
	public Slice<Long> findInquiryIds(final MemberId memberId,
		final Long idx, final Pageable pageable) {

		List<Long> ids = query
			.select(inquiry.id)
			.from(inquiry)
			.innerJoin(member).on(inquiry.createdBy.eq(member.id))
			.where(
				inquiryIdLt(idx)
			)
			.groupBy(inquiry.id)
			.orderBy(
				sort(pageable.getSort())
			)
			.limit(pageable.getPageSize() + 1)
			.fetch();

		return checkLastPage(pageable, ids);
	}

	@Override
	public List<InquiryModel> findInquiries(final List<Long> ids, final Pageable pageable) {

		return query
			.select(Projections.constructor(
					InquiryModel.class,
					inquiry.id,
					inquiry.category,
					inquiry.title,
					inquiry.contents,
					inquiry.createdAt,
					inquiry.updatedAt,
					inquiry.state,
					inquiry.answer,
					inquiry.answeredAt
				)
			)
			.from(inquiry)
			.innerJoin(member).on(inquiry.createdBy.eq(member.id))
			.where(
				inquiryIdIn(ids)
			)
			.orderBy(
				sort(pageable.getSort())
			).fetch();
	}

	public Inquiry findInquiry(final Long inquiryId, final MemberId memberId) {

		return query
			.selectFrom(inquiry)
			.innerJoin(member).on(inquiry.createdBy.eq(member.id))
			.where(
				inquiryIdEq(inquiryId),
				memberIdEq(memberId)
			).fetchFirst();
	}

	//////////////////////////////////////////////////////////////////

	private BooleanExpression memberIdEq(final MemberId memberId) {

		return member.accountEmail.eq(memberId.accountEmail())
			.and(member.oauthId.oauthServerType.eq(memberId.oauthServerType()));
	}

	private BooleanExpression inquiryIdEq(final Long inquiryId) {

		return inquiryId != null
			? inquiry.id.eq(inquiryId)
			: null;
	}

	private BooleanExpression inquiryIdIn(final List<Long> inquiryIds) {

		return (inquiryIds != null || inquiryIds.size() != 0)
			? inquiry.id.in(inquiryIds)
			: null;
	}

	private BooleanExpression inquiryIdLt(final Long inquiryId) {

		return inquiryId != null
			? inquiry.id.lt(inquiryId)
			: null;
	}

	// pagination
	private <T> Slice<T> checkLastPage(final Pageable pageable, final List<T> results) {

		boolean hasNext = false;

		// 조회된 개수(ex. 10) > 요청 페이지 사이즈(ex. 5)면 뒤에 조회될 페이지가 남아 있다는 것
		if (results.size() > pageable.getPageSize()) {
			hasNext = true;
			results.remove(pageable.getPageSize());
		}

		return new SliceImpl<>(results, pageable, hasNext);
	}

	private OrderSpecifier[] sort(final Sort sort) {

		List<OrderSpecifier> result = new ArrayList<>();

		// default
		result.add(new OrderSpecifier(Order.DESC, inquiry.createdAt)); // 최신순
		result.add(new OrderSpecifier(Order.DESC, inquiry.id));

		return result.toArray(new OrderSpecifier[result.size()]);
	}
}
