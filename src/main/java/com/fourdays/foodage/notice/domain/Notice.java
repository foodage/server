package com.fourdays.foodage.notice.domain;

import com.fourdays.foodage.common.domain.BaseTimeEntity;
import com.fourdays.foodage.common.enums.NoticeCategory;
import com.fourdays.foodage.common.exception.ExceptionInfo;
import com.fourdays.foodage.notice.dto.CreateNoticeRequest;
import com.fourdays.foodage.notice.exception.NoticeModifyException;
import com.querydsl.core.util.StringUtils;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "notice")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Notice extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(
		name = "title",
		nullable = false,
		length = 50
	)
	private String title;

	@Column(
		name = "content",
		nullable = false,
		length = 1500
	)
	private String contents;

	@Column(
		name = "category",
		nullable = false
	)
	@Enumerated(EnumType.STRING)
	private NoticeCategory category;

	@Column(name = "views")
	private int views;

	@Column(
		name = "created_by",
		nullable = false
	)
	private Long createdBy; // memberId

	public Notice(final CreateNoticeRequest request, final Long memberId) {
		this.title = request.title();
		this.contents = request.contents();
		this.category = request.category();
		this.createdBy = memberId;
	}

	public void addViews() {
		views++;
	}

	public void modify(final NoticeCategory category, final String title,
		final String contents) {

		if (StringUtils.isNullOrEmpty(title) ||
			StringUtils.isNullOrEmpty(contents)) {
			throw new NoticeModifyException(ExceptionInfo.ERR_NOTICE_MODIFY);
		}
		this.category = category;
		this.title = title;
		this.contents = contents;
	}
}
