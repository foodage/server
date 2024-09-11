package com.fourdays.foodage.review.domain;

import com.fourdays.foodage.common.domain.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

@Table(name = "review_image")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class ReviewImage extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "review_id", nullable = false)
	private Long reviewId;

	@Column(
		name = "image_url",
		nullable = false,
		length = 512
	)
	private String imageUrl;

	@Column(
		name = "sequence",
		nullable = false,
		columnDefinition = "TINYINT(1)"
	)
	@Min(1)
	@Max(5)
	private int sequence; // 이미지 업로드 순서

	@Column(
		name = "is_thumbnail",
		nullable = false,
		columnDefinition = "TINYINT(1)"
	)
	private boolean isThumbnail;
}
