package com.fourdays.foodage.member.domain;

import com.fourdays.foodage.member.vo.MemberId;

public interface MemberCustomRepository {

	int findReviewCountByMemberId(MemberId memberId);
}
