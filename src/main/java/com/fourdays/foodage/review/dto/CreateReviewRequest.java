package com.fourdays.foodage.review.dto;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CreateReviewRequest {

	@NotBlank
	private String restaurant;

	@NotBlank
	private String address;

	@DecimalMin("0.0")
	@DecimalMax("5.0")
	private float rating;

	@Size(max = 1000)
	private String contents;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull
	private LocalDateTime date;
}
