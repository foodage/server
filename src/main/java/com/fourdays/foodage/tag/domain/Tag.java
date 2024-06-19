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

@Table(name = "tag")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class Tag extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "name", nullable = false, length = 32)
	private String name;

	@Column(name = "bg_color", length = 128, nullable = false)
	private String bgColor;

	@Column(name = "text_color", length = 128, nullable = false)
	private String textColor;

	@Column(name = "description", length = 128)
	private String description;

	@Column(name = "creator_id", nullable = false)
	private Long creatorId; // memberId
}
