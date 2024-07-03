package com.fourdays.foodage.review.domain;

import com.fourdays.foodage.common.domain.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
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

	@Column(name = "restaurant", nullable = false)
	@NotNull
	private String restaurant;

	@Column(name = "address", nullable = false)
	@NotNull
	private String address;

	@Column(name = "menu")
	private String menu;

	@Column(name = "price")
	private int price;

	@Column(name = "rating", nullable = false)
	@NotNull
	private float rating;

	@Column(name = "contents")
	private String contents;

	@Column(name = "tag_id", nullable = false)
	@NotNull
	private Long tagId;

	@Column(name = "thumbnail_id")
	private Long thumbnailId;

	@Column(name = "last_eaten_food", nullable = false)
	private String lastEatenFood;

	@Column(name = "creator_id", nullable = false)
	private Long creatorId; // memberId
}
