package com.fourdays.foodage.member.domain;

import com.fourdays.foodage.member.vo.MemberId;
import com.fourdays.foodage.tag.domain.TagCategory;

public interface MemberCustomRepository {

	int findReviewCountByMemberId(MemberId memberId);

	int findTagCountByMemberId(MemberId memberId, TagCategory tagCategory);
}
