package com.fourdays.foodage.review.domain;

import com.fourdays.foodage.common.domain.BaseTimeEntity;
import com.fourdays.foodage.review.dto.CreateReviewRequest;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "review")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
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

	@Column(name = "date", nullable = false)
	private LocalDateTime date;

	@Column(name = "thumbnail_id")
	private Long thumbnailId;

	@Column(
		name = "created_by",
		nullable = false
	)
	private Long createdBy; // memberId

	public Review(CreateReviewRequest request, Long memberId) {
		this.restaurant = request.getRestaurant();
		this.address = request.getAddress();
		this.rating = request.getRating();
		this.contents = request.getContents();
		this.createdBy = memberId;
	}
}
