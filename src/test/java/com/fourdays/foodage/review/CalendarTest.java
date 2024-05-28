package com.fourdays.foodage.review;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;

import org.junit.jupiter.api.Test;

/**
 * author         : ebkim <br/>
 * date           : 24-03-06 <br/>
 * description    : 캘린더 조회 테스트  <br/>
 */
public class CalendarTest {

	private final int MINUS = 0;
	private final int PLUS = 1;

	@Test
	void 시작() {
		// 시작 일요일 : 7      -> 0칸 필요
		// 토요일 : 6          -> 6칸 필요
		// 금요일 : 5          -> 5칸 필요
		// 목요일 : 4          -> 4칸 필요

		System.out.println("🌕 : 기준일");
		System.out.println("🌓 : 이번달 일로 채워지는 칸");
		System.out.println("🌑 : 다음달 일로 채워지는 칸");
		System.out.println();

		System.out.println("일\t월\t화\t수\t목\t금\t토");
		System.out.println("🌕\t🌓\t🌓\t🌓\t🌓\t🌓\t🌓");
		System.out.println("1\t2\t3\t4\t5\t6\t7");
		System.out.print("value : " + DayOfWeek.SUNDAY.getValue() + " (일요일)");
		System.out.println("\t\t-> " + (DayOfWeek.SUNDAY.getValue() % 7) + "칸 필요");

		System.out.println("----------------------------------");

		System.out.println("일\t월\t화\t수\t목\t금\t토");
		System.out.println("🌑\t🌑\t🌑\t🌑\t🌑\t🌑\t🌕");
		System.out.println("26\t27\t28\t29\t30\t31\t1");
		System.out.print("value : " + DayOfWeek.SATURDAY.getValue() + " (토요일)");
		System.out.println("\t\t-> " + (DayOfWeek.SATURDAY.getValue() % 7) + "칸 필요");

		System.out.println("----------------------------------");

		System.out.println("일\t월\t화\t수\t목\t금\t토");
		System.out.println("🌑\t🌑\t🌑\t🌑\t🌑\t🌕\t🌓");
		System.out.println("27\t28\t29\t30\t31\t1\t2");
		System.out.print("value : " + DayOfWeek.FRIDAY.getValue() + " (금요일)");
		System.out.println("\t\t-> " + (DayOfWeek.FRIDAY.getValue() % 7) + "칸 필요");

		System.out.println("----------------------------------");
	}

	@Test
	void 마지막() {
		// 마지막 일요일 : 7     -> 6칸 필요
		// 토요일 : 6          -> 0칸 필요
		// 금요일 : 5          -> 1칸 필요
		// 목요일 : 4          -> 2칸 필요
		System.out.println("🌕 : 기준일");
		System.out.println("🌓 : 이번달 일로 채워지는 칸");
		System.out.println("🌑 : 다음달 일로 채워지는 칸");
		System.out.println();

		System.out.println("일\t월\t화\t수\t목\t금\t토");
		System.out.println("🌕\t🌑\t🌑\t🌑\t🌑\t🌑\t🌑");
		System.out.println("31\t1\t2\t3\t4\t5\t6");
		System.out.print("value : " + DayOfWeek.SUNDAY.getValue() + " (일요일)");
		System.out.println("\t\t-> " + (7 - (DayOfWeek.SUNDAY.getValue() % 7 + 1)) + "칸 필요");

		System.out.println("----------------------------------");

		System.out.println("일\t월\t화\t수\t목\t금\t토");
		System.out.println("🌓\t🌓\t🌓\t🌓\t🌓\t🌓\t🌕");
		System.out.println("25\t26\t27\t28\t29\t30\t31");
		System.out.print("value : " + DayOfWeek.SATURDAY.getValue() + " (토요일)");
		System.out.println("\t\t-> " + (7 - (DayOfWeek.SATURDAY.getValue() % 7 + 1)) + "칸 필요");

		System.out.println("----------------------------------");

		System.out.println("일\t월\t화\t수\t목\t금\t토");
		System.out.println("🌓\t🌓\t🌓\t🌓\t🌓\t🌕\t🌑");
		System.out.println("26\t27\t28\t29\t30\t31\t1");
		System.out.print("value : " + DayOfWeek.FRIDAY.getValue() + " (금요일)");
		System.out.println("\t\t-> " + (7 - (DayOfWeek.FRIDAY.getValue() % 7 + 1)) + "칸 필요");

		System.out.println("----------------------------------");
	}

	@Test
	void getCalendar() {

		// 현재 날짜 가져오기
		// LocalDate current = LocalDate.now();
		LocalDate current = LocalDate.of(2023, Month.SEPTEMBER, 5);
		System.out.println("### 오늘 : " + current);

		//////////////// 이번달 ////////////////
		LocalDate firstDayOfMonth = current.withDayOfMonth(1);
		LocalDate lastDayOfMonth = current.withDayOfMonth(current.lengthOfMonth());
		DayOfWeek convertFirstDayOfWeek = firstDayOfMonth.getDayOfWeek(); // 3월 기준 금
		DayOfWeek convertLastDayOfWeek = lastDayOfMonth.getDayOfWeek(); // 3월 기준 일

		//////////////// 지난달 ////////////////
		int previousData = convertFirstDayOfWeek.getValue() % 7;
		// 이번달 1일에서 하루 빼서, 직전달의 마지막 일을 얻어옴
		LocalDate previousMonthLastDay = firstDayOfMonth.minusDays(1);

		System.out.println();
		System.out.println("# 필요한 칸수 : " + previousData);
		if (previousData != 0) {
			printCalcDate(previousMonthLastDay, previousData, MINUS);
			printDateRange("--- 저번달 날짜 범위: ",
				previousMonthLastDay.minusDays(previousData - 1), previousMonthLastDay);
			// 마지막 날도 포함해서 카운팅 해야하므로, -1

		}

		//////////////// 이번달 ////////////////
		System.out.println();
		System.out.println("--- 이번달 시작: " + firstDayOfMonth
			+ " (" + KoreanDay.of(convertFirstDayOfWeek) + ")");
		System.out.println("--- 이번달 마지막: " + lastDayOfMonth
			+ " (" + KoreanDay.of(convertLastDayOfWeek) + ")");

		//////////////// 다음달 ////////////////
		int nextData = 6 - (convertLastDayOfWeek.getValue() % 7);
		// 이번달 말일에서 하루 더해서, 다음달의 1일을 얻어옴
		LocalDate nextMonthFirstDay = lastDayOfMonth.plusDays(1);

		System.out.println();
		System.out.println("# 필요한 칸수 : " + nextData);
		if (nextData != 0) {
			printCalcDate(nextMonthFirstDay, nextData, PLUS);
			printDateRange("--- 다음달 날짜 범위 : ", nextMonthFirstDay,
				nextMonthFirstDay.plusDays(nextData - 1));
			// 마지막 날도 포함해서 카운팅 해야하므로, -1
		}
	}

	@Test
	void drawCalendar() {

		// 현재 날짜 가져오기
		LocalDate current = LocalDate.now();
		// LocalDate current = LocalDate.of(2023, Month.OCTOBER, 5);

		//////////////// 이번달 ////////////////
		LocalDate firstDayOfMonth = current.withDayOfMonth(1);
		LocalDate lastDayOfMonth = current.withDayOfMonth(current.lengthOfMonth());
		DayOfWeek convertFirstDayOfWeek = firstDayOfMonth.getDayOfWeek(); // 3월 기준 금
		DayOfWeek convertLastDayOfWeek = lastDayOfMonth.getDayOfWeek(); // 3월 기준 일

		//////////////// 지난달 ////////////////
		int previousData = convertFirstDayOfWeek.getValue() % 7;
		LocalDate previousMonthLastDay = firstDayOfMonth.minusDays(1);
		// 이번달 1일에서 하루 빼서, 직전달의 마지막 일을 얻어옴

		//////////////// 다음달 ////////////////
		int nextData = 6 - (convertLastDayOfWeek.getValue() % 7);
		LocalDate nextMonthFirstDay = lastDayOfMonth.plusDays(1);
		// 이번달 말일에서 하루 더해서, 다음달의 1일을 얻어옴

		drawMonthCalendar(firstDayOfMonth, lastDayOfMonth,
			previousData, previousMonthLastDay,
			nextData, nextMonthFirstDay);
	}

	private void drawMonthCalendar(LocalDate startDate, LocalDate endDate,
		int previousData, LocalDate previousMonthLastDay,
		int nextData, LocalDate nextMonthFirstDay) {

		LocalDate currentDate = startDate;

		System.out.println();
		System.out.println("\t\t  " + currentDate.getYear());
		System.out.println("\t\t  " + currentDate.getMonth());
		System.out.println();

		System.out.println("일\t월\t화\t수\t목\t금\t토");

		LocalDate previousMonthFirstDay = previousMonthLastDay.minusDays(previousData - 1);
		LocalDate nextMonthLastDay = nextMonthFirstDay.plusDays(nextData - 1);

		int dayOfWeekValue = currentDate.getDayOfWeek().getValue();

		// 시작일까지의 공백 출력
		for (int i = 1; i <= dayOfWeekValue; i++) {
			while (!previousMonthFirstDay.isAfter(previousMonthLastDay)) {
				System.out.print(previousMonthFirstDay.getDayOfMonth() + "\t");
				if (previousMonthFirstDay.getDayOfWeek() == DayOfWeek.SATURDAY) {
					System.out.println(); // 토요일마다 줄 바꿈
				}

				previousMonthFirstDay = previousMonthFirstDay.plusDays(1);
			}
		}

		// 날짜 출력
		while (!currentDate.isAfter(endDate)) {
			System.out.print(currentDate.getDayOfMonth() + "\t");

			if (currentDate.getDayOfWeek() == DayOfWeek.SATURDAY) {
				System.out.println(); // 토요일마다 줄 바꿈
			}

			currentDate = currentDate.plusDays(1);
		}

		if (nextData != 0) {
			while (!nextMonthFirstDay.isAfter(nextMonthLastDay)) {
				System.out.print(nextMonthFirstDay.getDayOfMonth() + "\t");
				if (nextMonthFirstDay.getDayOfWeek() == DayOfWeek.SATURDAY) {
					System.out.println(); // 토요일마다 줄 바꿈
				}

				nextMonthFirstDay = nextMonthFirstDay.plusDays(1);
			}
		}

		System.out.println(); // 마지막 날 다음 줄로 이동
		System.out.println();
	}

	private void printCalcDate(LocalDate date, int count, int kind) {
		LocalDate date_ = null;
		String operand = "";
		switch (kind) {
			case MINUS -> {
				date_ = date.minusDays(count);
				operand = " - ";
			}
			case PLUS -> {
				date_ = date.plusDays(count);
				operand = " + ";
			}
		}
		System.out.println(
			"# " + date + operand + count + "일 = " + date_);
	}

	private void printDateRange(String label, LocalDate startDate, LocalDate endDate) {
		System.out.println(label + startDate + " ~ " + endDate);
	}

	private enum KoreanDay {
		월(DayOfWeek.MONDAY),
		화(DayOfWeek.TUESDAY),
		수(DayOfWeek.WEDNESDAY),
		목(DayOfWeek.THURSDAY),
		금(DayOfWeek.FRIDAY),
		토(DayOfWeek.SATURDAY),
		일(DayOfWeek.SUNDAY);

		private final DayOfWeek dayOfWeek;

		KoreanDay(DayOfWeek dayOfWeek) {
			this.dayOfWeek = dayOfWeek;
		}

		// DayOfWeek를 받아 해당하는 KoreanDay 인스턴스를 반환하는 메서드
		public static String of(DayOfWeek dayOfWeek) {
			for (KoreanDay koreanDay : values()) {
				if (koreanDay.dayOfWeek == dayOfWeek) {
					return koreanDay.name();
				}
			}
			throw new IllegalArgumentException("Invalid DayOfWeek: " + dayOfWeek);
		}

		public String getKoreanName() {
			// 이렇게 하면 '월' > '월요일'으로 반환됨
			return dayOfWeek.getDisplayName(TextStyle.FULL, Locale.KOREAN);
		}
	}
}