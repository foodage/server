package com.fourdays.foodage.tag.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fourdays.foodage.home.dto.TagUsageRankResponse;
import com.fourdays.foodage.jwt.util.SecurityUtil;
import com.fourdays.foodage.member.vo.MemberId;
import com.fourdays.foodage.tag.service.TagService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@Tag(name = "tag", description = "태그 관련 api")
public class TagController {

	private final TagService tagService;

	public TagController(TagService tagService) {
		this.tagService = tagService;
	}

	@Operation(summary = "태그 사용 순위 조회")
	@GetMapping("/tag/usage-rank")
	public ResponseEntity<List<TagUsageRankResponse>> getTagUsageRank() {

		MemberId memberId = SecurityUtil.getCurrentMemberId();
		List<TagUsageRankResponse> response = tagService.getTagUsageRank(memberId);

		return ResponseEntity.ok().body(response);
	}
}
