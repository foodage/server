package com.fourdays.foodage.inquiry.domain;

import java.time.LocalDateTime;

import com.fourdays.foodage.common.domain.BaseTimeEntity;
import com.fourdays.foodage.common.enums.InquiryCategory;
import com.fourdays.foodage.common.enums.InquiryState;
import com.fourdays.foodage.common.exception.ExceptionInfo;
import com.fourdays.foodage.inquiry.exception.InquiryAnswerContentsException;

import io.netty.util.internal.StringUtil;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "inquiry")
@Entity
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Inquiry extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(
		name = "category",
		nullable = false
	)
	@Enumerated(EnumType.STRING)
	private InquiryCategory category;

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
		name = "created_by"
	)
	private Long createdBy; // memberId

	@Column(
		name = "notify_email",
		nullable = false
	)
	private String notifyEmail;

	@Column(
		name = "state",
		nullable = false
	)
	@Enumerated(EnumType.STRING)
	private InquiryState state;

	@Column(
		name = "answer",
		length = 1500
	)
	private String answer;

	@Column(name = "answered_by")
	private Long answeredBy;

	@Column(name = "answered_at")
	private LocalDateTime answeredAt;

	public void modifyInquiry(final String title, final String contents) {
		this.title = title;
		this.answer = contents;
	}

	public void registerAnswer(final String contents, final Long adminId) {
		if (StringUtil.isNullOrEmpty(contents)) {
			throw new InquiryAnswerContentsException(ExceptionInfo.ERR_INQUIRY_ANSWER_CONTENTS_EMPTY);
		}
		this.answer = contents;
		this.answeredBy = adminId;
		this.answeredAt = LocalDateTime.now();
		this.state = InquiryState.ANSWERED;
	}
}
