package com.fourdays.foodage.review.domain;

import java.time.LocalDateTime;

import com.fourdays.foodage.common.domain.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "review")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class Review extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "place")
	private String place;

	@Column(name = "menu", nullable = false)
	private String menu;

	@Column(name = "price")
	private Integer price;

	@Column(name = "rating", nullable = false)
	private Integer rating;

	@Column(name = "review", nullable = false)
	private String review;

	@Column(name = "tag")
	private String tag;

	@Column(name = "image")
	private String image;

	@Column(name = "date")
	private LocalDateTime date;

}
