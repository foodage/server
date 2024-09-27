package com.fourdays.foodage.inquiry.domain;

import java.util.List;

import org.springframework.data.domain.Slice;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class InquiriesResponse {

	private int totalCount;

	private Paging paging;

	private List<InquiryModel> inquiries;

	public InquiriesResponse(Slice<Long> inquiryIds,
		List<InquiryModel> inquiries, int totalCount) {

		this.totalCount = totalCount;

		Long nextId = inquiries.isEmpty()
			? null
			: inquiries.get(inquiries.size() - 1).getId();
		this.paging = new Paging(inquiryIds.getPageable().getPageSize(),
			inquiryIds.hasNext(), nextId);

		this.inquiries = inquiries;
	}

	@AllArgsConstructor
	@Getter
	public static class Paging {

		private int size;

		private boolean hasNext;

		private Long nextId;
	}
}
