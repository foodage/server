package com.fourdays.foodage.member.dto;

import java.time.LocalDateTime;

public record MemberLeaveResponseDto(

	boolean hasLeaveRequested,
	LocalDateTime leaveRequestAt
) {
}
