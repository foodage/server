package com.fourdays.foodage.collection.domain;

import static jakarta.persistence.EnumType.*;

import com.fourdays.foodage.common.domain.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "achievement")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class Achievement extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(
		name = "name",
		nullable = false,
		length = 50
	)
	private String name;

	@Column(
		name = "description",
		nullable = false,
		length = 255
	)
	private String description;

	@Column(name = "reword", nullable = false)
	@Enumerated(STRING)
	private RewardType reword;

	@Column(name = "condition_type", nullable = false)
	@Enumerated(STRING)
	private ConditionType conditionType;

	@Column(name = "condition_detail_type", length = 255)
	private String conditionDetailType;

	@Column(name = "condition_value", nullable = false)
	private String conditionValue;

	// @PrePersist
	// protected void onCreate() {
	// 	this.createdAt = LocalDateTime.now();
	// 	this.updatedAt = LocalDateTime.now();
	// }
	//
	// @PreUpdate
	// protected void onUpdate() {
	// 	this.updatedAt = LocalDateTime.now();
	// }

	public Object getFormattedConditionValue() {
		switch (conditionType) {
			case REVIEW_COUNT, TAG_USAGE -> {
				return Integer.parseInt(conditionValue);
			}
			default -> {
				return conditionValue;
			}
		}
	}
}
