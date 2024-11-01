package com.fourdays.foodage.collection.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberAchievementRepository extends JpaRepository<MemberAchievement, Long> {

	boolean existsByMemberIdAndAchievementId(Long memberId, Long achievementId);
}
