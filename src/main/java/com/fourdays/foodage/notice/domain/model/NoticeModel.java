package com.fourdays.foodage.notice.domain.model;

import java.time.LocalDateTime;

import com.fourdays.foodage.common.domain.BaseTimeEntity;
import com.fourdays.foodage.common.enums.CharacterType;
import com.fourdays.foodage.common.enums.NoticeCategory;
import com.fourdays.foodage.notice.dto.CreateNoticeRequest;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class NoticeModel extends BaseTimeEntity {

	private Long id;

	private String title;

	private String contents;

	private NoticeCategory category;

	private int views;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

	private Creator creator; // memberId

	public NoticeModel(final CreateNoticeRequest request) {
		this.title = request.title();
		this.contents = request.contents();
		this.category = request.category();
	}

	public NoticeModel(Long id, NoticeCategory category, String title,
		String contents, int views, LocalDateTime createdAt,
		LocalDateTime updatedAt, Creator creator) {

		this.id = id;
		this.title = title;
		this.contents = contents;
		this.category = category;
		this.views = views;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.creator = creator;
	}

	@AllArgsConstructor
	@Getter
	public static class Creator {

		private String nickname;

		private CharacterType character;
	}
}
