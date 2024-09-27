package com.fourdays.foodage.inquiry.domain;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import com.fourdays.foodage.member.vo.MemberId;

public interface InquiryCustomRepository {

	int countByInquiryId(final MemberId memberId);

	Slice<Long> findInquiryIds(final MemberId memberId, final Long idx, final Pageable pageable);

	List<InquiryModel> findInquiries(final List<Long> ids, final Pageable pageable);
}
