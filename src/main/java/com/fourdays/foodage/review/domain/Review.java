package com.fourdays.foodage.review.domain;

import com.fourdays.foodage.common.domain.BaseTimeEntity;
import com.fourdays.foodage.review.dto.CreateReviewRequest;

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

	@Column(name = "restaurant")
	private String restaurant;

	@Column(name = "address")
	private String address;

	@Column(
		name = "rating",
		nullable = false
	)
	private float rating;

	@Column(
		name = "contents",
		nullable = false,
		length = 1024
	)
	private String contents;

	@Column(name = "thumbnail_id")
	private Long thumbnailId;

	@Column(
		name = "creator_id",
		nullable = false
	)
	private Long creatorId; // memberId
}
