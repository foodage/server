package com.fourdays.foodage.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum NoticeCategory {

	ALL,
	EVENT,          // 이벤트
	RELEASE_NOTE,   // 버전 업데이트
	BUG_FIXES,      // 버그 수정
	IMPROVEMENT,    // 기능 개선
	WHATS_NEW,      // 신규 기능 소개, 소식 전달
	NEWS,           // 일반 소식
}
