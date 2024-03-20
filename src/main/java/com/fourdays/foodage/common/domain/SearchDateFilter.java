package com.fourdays.foodage.common.domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import io.netty.util.internal.StringUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import lombok.Getter;

@JsonTypeInfo(
	use = JsonTypeInfo.Id.NAME,
	include = JsonTypeInfo.As.WRAPPER_OBJECT
)
@JsonRootName(value = "searchValue")
@Getter
public class SearchDateFilter extends BaseSearchFilter {

	@Schema(title = "검색 시작일")
	private String startDate; // from

	@Schema(title = "검색 종료일")
	private String endDate; // to

	@Nullable
	private Integer maxPeriodByDays;

	public SearchDateFilter(String startDate, String endDate, Integer maxPeriodByDays) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.maxPeriodByDays = maxPeriodByDays;
		validateMaxPeriodByDays();
	}

	public String getStartDate() {

		if (StringUtil.isNullOrEmpty(startDate)) {
			String pastDate = LocalDate.now().minusMonths(maxPeriodByDays)
				.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
			return pastDate;
		}
		return startDate;
	}

	public String getEndDate() {

		if (StringUtil.isNullOrEmpty(startDate)) {
			String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
			return currentDate;
		}
		return endDate;
	}

	public void validateMaxPeriodByDays() {
		if (maxPeriodByDays == null) {
			maxPeriodByDays = 0; // default 제한 없음
		}
		validateDate();
	}

	private void validateDate() {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd"); // 입력된 형식을 지정
		LocalDate formatStartDate = LocalDate.parse(getStartDate(), formatter);
		LocalDate formatEndDate = LocalDate.parse(getEndDate(), formatter);

		long gap = ChronoUnit.DAYS.between(formatStartDate, formatEndDate);
		if (gap > maxPeriodByDays) {
			throw new IllegalArgumentException("조회 가능한 최대 범위를 초과하였습니다. 1년 범위로 입력하셈");
		}
	}
}
