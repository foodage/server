package com.fourdays.foodage.collection.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.fourdays.foodage.collection.domain.ConditionType;
import com.fourdays.foodage.tag.domain.TagCategory;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(AchievementTriggers.class)
public @interface AchievementTrigger {

	// 조건 유형 (예: "diary_count", "tag_usage")
	ConditionType conditionType();

	// 세부 태그 타입 (ALL, ASIAN 등)
	TagCategory[] conditionDetailType() default {};

	// 조건 값 (예: "10", "A")
	String conditionValue();
}
