package com.fourdays.foodage.collection.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AchievementRepository extends JpaRepository<Achievement, Long> {

	List<Achievement> findByConditionTypeAndConditionValue(ConditionType conditionType, String conditionValue);
}

