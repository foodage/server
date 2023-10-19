package com.fourdays.foodage.common.dto;

import java.util.Set;
import java.util.stream.Collectors;

import com.fourdays.foodage.common.enums.BadRequestCode;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BaseResponse<T> {
	private int resultCode;
	private String resultMsg;
	private T resultData;

	public static <T> BaseResponse<T> success() {
		return BaseResponse.<T>builder()
			.resultCode(BadRequestCode.SUCCESS.getResultCode())
			.resultMsg(BadRequestCode.SUCCESS.getResultMsg())
			.build();
	}

	private static <T> BaseResponse<T> successData(T data) {
		return BaseResponse.<T>builder()
			.resultCode(BadRequestCode.SUCCESS.getResultCode())
			.resultMsg(BadRequestCode.SUCCESS.getResultMsg())
			.resultData(data)
			.build();
	}

	public static <T> BaseResponse<T> error(BadRequestCode errorCode) {
		return BaseResponse.<T>builder()
			.resultCode(errorCode.getResultCode())
			.resultMsg(errorCode.getResultMsg())
			.build();
	}

	public static <T> BaseResponse<T> error(BadRequestCode errorCode, String errorMsg) {
		return BaseResponse.<T>builder()
			.resultCode(errorCode.getResultCode())
			.resultMsg(errorMsg)
			.build();
	}

	public static <T> BaseResponse<T> error(BadRequestCode errorCode, String errorMsg, Object... args) {
		return BaseResponse.<T>builder()
			.resultCode(errorCode.getResultCode())
			.resultMsg(String.format(errorMsg, args))
			.build();
	}

	// 차후 Set<String> -> Set<T>로의 처리 고려
	public static <T> BaseResponse<T> error(BadRequestCode errorCode, String errorMsg, Set<String> args) {
		return BaseResponse.<T>builder()
			.resultCode(errorCode.getResultCode())
			.resultMsg(errorMsg)
			.resultData((T)args.stream().collect(Collectors.joining(", ")))
			.build();
	}
}

