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
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "review_image")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
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
		nullable = false,
		updatable = false
	)
	@Size(max = 64)
	private String format;

	@Column(
		name = "sequence",
		nullable = false,
		columnDefinition = "TINYINT(1)"
	)
	@Min(1)
	@Max(5)
	private int sequence; // 이미지 업로드 순서

	@Column(
		name = "use_thumbnail",
		nullable = false,
		columnDefinition = "TINYINT(1)"
	)
	private boolean useThumbnail;

	private String domain;

	public ReviewImage(Long reviewId, String uploadPath, String fileFormat,
		int sequence, boolean useThumbnail) {

		this.reviewId = reviewId;
		this.imageUrl = domain + uploadPath;
		this.format = fileFormat;
		this.sequence = sequence;
		this.useThumbnail = useThumbnail;
	}
}
