package com.fourdays.foodage.tag.service;

import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fourdays.foodage.home.dto.TagUsageRankResponse;
import com.fourdays.foodage.member.vo.MemberId;
import com.fourdays.foodage.tag.domain.TagCustomRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TagService {

	private final TagCustomRepository tagCustomRepository;

	public TagService(TagCustomRepository tagCustomRepository) {
		this.tagCustomRepository = tagCustomRepository;
	}

	public List<TagUsageRankResponse> getTagUsageRank(MemberId memberId) {

		List<TagUsageRankResponse> response = tagCustomRepository.findTagUsageRank(memberId);

		// tag 사용 횟수 desc 정렬
		response.sort(Comparator.comparingLong(TagUsageRankResponse::getUsageCount)
			.reversed());
		updateRanks(response);

		return response;
	}

	private void updateRanks(List<TagUsageRankResponse> response) {

		int currentRank = 1;
		for (int i = 0; i < response.size(); i++) {
			if (i > 0 && response.get(i).getUsageCount() < response.get(i - 1).getUsageCount()) {
				currentRank = i + 1;
			}
			response.get(i).updateRank(currentRank);
		}
	}
}
