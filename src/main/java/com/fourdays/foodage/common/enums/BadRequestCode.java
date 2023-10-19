package com.fourdays.foodage.common.enums;

public enum BadRequestCode {

	SUCCESS(0, "요청 성공", ""),
	ERROR(-9999, "서버 에러", "Internal Server Error"),

	// COMMON /////////////////////////////////////////////////////////////////////////////////////////
	// exception handler
	ERR_INTERNAL(-1000, "서버에 문제가 발생했습니다.", ""),
	ERR_REQUIRED_FIELD(-1001, "입력 파라미터가 유효하지 않습니다. 파라미터를 다시 확인해주세요.", ""),
	ERR_ACCESS_DENIED(-1002, "해당 컨텐츠에 접근할 수 있는 권한이 없습니다.", ""),
	ERR_SESSION_EXPIRED(-1003, "장시간 동작이 없어 로그아웃됩니다.", ""),

	// USER /////////////////////////////////////////////////////////////////////////////////////////
	ERR_USER_NOT_FOUND(-10000, "일치하는 유저를 찾을 수 없습니다.", ""),

	;

	private final int resultCode; // 에러 코드
	private final String resultMsg; // 클라이언트 반환 메시지
	private final String loggingMsg; // 서버 로깅 메시지
	private String customResultMsg; // 클라이언트 반환 메시지 (포맷스트링 사용할 시)

	BadRequestCode(int errCode, String message, String loggingMsg) {
		this.resultCode = errCode;
		this.resultMsg = message;
		this.loggingMsg = loggingMsg;
		this.customResultMsg = "";
	}

	public int getResultCode() {
		return resultCode;
	}

	public String getResultMsg() {
		return customResultMsg.isEmpty() // 커스텀 메시지가 있을 경우
			? resultMsg
			: customResultMsg;
	}

	public String customResultMsg(String customMsg) {
		if (customMsg.isEmpty()) {
			throw new IllegalArgumentException();
		}
		this.customResultMsg = customMsg;
		return this.customResultMsg;
	}
}
