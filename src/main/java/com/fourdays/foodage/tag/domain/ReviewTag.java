package com.fourdays.foodage.tag.domain;

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

@Table(name = "review_tag")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class ReviewTag extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "review_id", nullable = false)
	private Long reviewId;

	@Column(name = "tag_id", nullable = false)
	private Long tagId;

	@Column(name = "bg_color", length = 128, nullable = false)
	private String tagBgColor;

	@Column(name = "text_color", length = 128, nullable = false)
	private String tagTextColor;

	@Column(name = "name", length = 128)
	private String tagName;
}
