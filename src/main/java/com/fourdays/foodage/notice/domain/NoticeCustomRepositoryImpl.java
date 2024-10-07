package com.fourdays.foodage.notice.domain;

import static com.fourdays.foodage.member.domain.QMember.*;
import static com.fourdays.foodage.notice.domain.QNotice.*;
import static com.querydsl.core.group.GroupBy.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.fourdays.foodage.notice.domain.model.NoticeModel;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class NoticeCustomRepositoryImpl implements NoticeCustomRepository {

	private final JPAQueryFactory query;

	public NoticeCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
		this.query = jpaQueryFactory;
	}

	@Override
	public NoticeModel findNotice(final Long noticeId) {

		NoticeModel noticeModel = query
			.select(Projections.constructor(
					NoticeModel.class,
					notice.id,
					notice.category,
					notice.title,
					notice.contents,
					notice.views,
					notice.createdAt,
					notice.updatedAt,
					Projections.constructor(
						NoticeModel.Creator.class,
						member.nickname,
						member.character
					)
				)
			)
			.from(notice)
			.innerJoin(member).on(notice.createdBy.eq(member.id))
			.where(
				noticeIdEq(noticeId)
			)
			.fetchFirst();

		return noticeModel;
	}

	@Override
	public int countByNoticeId() {

		int totalCount = query
			.select(notice.id)
			.from(notice)
			.innerJoin(member).on(notice.createdBy.eq(member.id))
			.where()
			.groupBy(notice.id)
			.fetch().size();

		return totalCount;
	}

	@Override
	public Slice<Long> findNoticeIds(final Long idx, final Pageable pageable) {

		List<Long> ids = query
			.select(notice.id)
			.from(notice)
			.innerJoin(member).on(notice.createdBy.eq(member.id))
			.where(
				noticeIdLt(idx)
			)
			.groupBy(notice.id)
			.orderBy(
				sort(pageable.getSort())
			)
			.limit(pageable.getPageSize() + 1)
			.fetch();

		return checkLastPage(pageable, ids);
	}

	@Override
	public List<NoticeModel> findNotices(final List<Long> ids, final Pageable pageable) {

		List<NoticeModel> noticeModels = query
			.select(
				notice.id,
				notice.category,
				notice.title,
				notice.contents,
				notice.views,
				notice.createdAt,
				notice.updatedAt,
				member.nickname,
				member.character
			)
			.from(notice)
			.innerJoin(member).on(notice.createdBy.eq(member.id))
			.where(
				noticeIdIn(ids)
			)
			.orderBy(
				sort(pageable.getSort())
			)
			.transform(
				groupBy(notice.id)
					.list(
						Projections.constructor(
							NoticeModel.class,
							notice.id,
							notice.category,
							notice.title,
							notice.contents,
							notice.views,
							notice.createdAt,
							notice.updatedAt,
							Projections.constructor(
								NoticeModel.Creator.class,
								member.nickname,
								member.character
							)
						)
					)
			);

		return noticeModels;
	}

	//////////////////////////////////////////////////////////////////

	private BooleanExpression noticeIdEq(final Long noticeId) {

		return noticeId != null
			? notice.id.eq(noticeId)
			: null;
	}

	private BooleanExpression noticeIdLt(final Long noticeId) {

		return noticeId != null
			? notice.id.lt(noticeId)
			: null;
	}

	private BooleanExpression noticeIdIn(final List<Long> noticeIds) {

		return (noticeIds != null || noticeIds.size() != 0)
			? notice.id.in(noticeIds)
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
		result.add(new OrderSpecifier(Order.DESC, notice.createdAt)); // 최신순
		result.add(new OrderSpecifier(Order.DESC, notice.id));

		return result.toArray(new OrderSpecifier[result.size()]);
	}
}
