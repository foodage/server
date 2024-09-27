package com.fourdays.foodage.inquiry.domain;

import java.time.LocalDateTime;

import com.fourdays.foodage.common.domain.BaseTimeEntity;
import com.fourdays.foodage.common.enums.InquiryCategory;

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
		name = "is_answered",
		nullable = false
	)
	private Boolean isAnswered;

	@Column(
		name = "answer",
		length = 1500
	)
	private String answer;

	@Column(name = "answered_by")
	private Long answeredBy;

	@Column(name = "answered_at")
	private LocalDateTime answeredAt;
}
