package com.fourdays.foodage.notice.dto;

import java.util.List;

import org.springframework.data.domain.Slice;

import com.fourdays.foodage.notice.domain.model.NoticeModel;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class NoticeResponse {

	private int totalCount;

	private Paging paging;

	private List<NoticeModel> notices;

	public NoticeResponse(Slice<Long> noticeIds,
		List<NoticeModel> notices, int totalCount) {

		this.totalCount = totalCount;

		Long nextId = notices.isEmpty() ? null : notices.get(notices.size() - 1).getId();
		this.paging = new Paging(noticeIds.getPageable().getPageSize(),
			noticeIds.hasNext(), nextId);

		this.notices = notices;
	}

	@AllArgsConstructor
	@Getter
	public static class Paging {

		private int size;

		private boolean hasNext;

		private Long nextId;
	}
}
