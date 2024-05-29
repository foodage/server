package com.fourdays.foodage.tag.domain;

import java.util.List;

import com.fourdays.foodage.home.dto.TagUsageRankResponse;
import com.fourdays.foodage.member.vo.MemberId;

public interface TagCustomRepository {

	List<TagUsageRankResponse> findTagUsageRank(MemberId memberId);
}
