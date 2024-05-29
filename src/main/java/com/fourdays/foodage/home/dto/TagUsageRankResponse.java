package com.fourdays.foodage.home.dto;

import lombok.Getter;

@Getter
public class TagUsageRankResponse {

	private Long tagId;

	private String tagName;

	private Long usageCount;

	private int rank;

	public TagUsageRankResponse(Long tagId, String tagName, Long usageCount) {
		this.tagId = tagId;
		this.tagName = tagName;
		this.usageCount = usageCount;
	}

	public void updateRank(int rank) {
		this.rank = rank;
	}
}
