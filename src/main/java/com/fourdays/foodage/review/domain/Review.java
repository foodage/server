package com.fourdays.foodage.review.domain;

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

	@Column(name = "price", nullable = false)
	private int price;

	@Column(name = "rating", nullable = false)
	private int rating;

	@Column(name = "contents", nullable = false)
	private String contents;

	@Column(name = "tag_id", nullable = false)
	private int tagId;

	@Column(name = "thumbnail_id")
	private int thumbnailId;

	@Column(name = "last_eaten_food", nullable = false)
	private String lastEatenFood;

	@Column(name = "created_by_member_id", nullable = false)
	private Long createdByMemberId;
}
