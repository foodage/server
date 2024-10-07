package com.fourdays.foodage.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fourdays.foodage.common.exception.ExceptionInfo;
import com.fourdays.foodage.oauth.exception.NotSupportedOauthServerTypeException;

public enum InquiryCategory {

	BUG_REPORT,    // 버그 신고하기
	SUGGESTION,    // 제안하기
	PRAISE,        // 칭찬하기
	OTHER          // 기타
	;

	@JsonCreator
	public static InquiryCategory from(String jsonValue) {
		for (InquiryCategory category : values()) {
			if (category.name().equalsIgnoreCase(jsonValue)) {
				return category;
			}
		}
		throw new NotSupportedOauthServerTypeException(ExceptionInfo.ERR_NOT_SUPPORTED_INQUIRY_CATEGORY);
	}
}
