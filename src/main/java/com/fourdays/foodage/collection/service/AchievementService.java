package com.fourdays.foodage.collection.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fourdays.foodage.collection.domain.Achievement;
import com.fourdays.foodage.collection.domain.AchievementRepository;
import com.fourdays.foodage.collection.domain.ConditionType;
import com.fourdays.foodage.collection.domain.MemberAchievement;
import com.fourdays.foodage.collection.domain.MemberAchievementRepository;
import com.fourdays.foodage.member.domain.Member;
import com.fourdays.foodage.member.service.MemberQueryService;
import com.fourdays.foodage.member.vo.MemberId;
import com.fourdays.foodage.review.domain.Review;
import com.fourdays.foodage.review.service.ReviewService;
import com.fourdays.foodage.tag.domain.TagCategory;

@Service
public class AchievementService {

	private final AchievementRepository achievementRepository;

	private final MemberAchievementRepository memberAchievementRepository;

	private final MemberQueryService memberQueryService;

	private final ReviewService reviewService;

	public AchievementService(AchievementRepository achievementRepository,
		MemberAchievementRepository memberAchievementRepository, MemberQueryService memberQueryService,
		ReviewService reviewService) {
		this.achievementRepository = achievementRepository;
		this.memberAchievementRepository = memberAchievementRepository;
		this.memberQueryService = memberQueryService;
		this.reviewService = reviewService;
	}

	@Transactional
	public void checkAndAssignAchievement(final Object[] args, final ConditionType conditionType,
		final TagCategory[] conditionDetailType, final String conditionValue) {

		Member member = null;
		Review review = null;
		for (Object arg : args) {
			if (arg instanceof Member) {
				member = (Member)arg;
			}
			if (arg instanceof Review) {
				review = (Review)arg;
			}
		}

		// 한 번에 여러개 수여 가능하다는 경우의 수도 가정해야함
		// 조건에 맞는 업적 조회
		List<Achievement> achievements = achievementRepository.findByConditionTypeAndConditionValue(
			conditionType, conditionValue);

		for (Achievement achievement : achievements) {
			if (isAchievementMet(member, review, achievement, conditionDetailType)) {
				// 이미 수여한 적이 있는지 확인
				boolean alreadyAchieved = memberAchievementRepository.existsByMemberIdAndAchievementId(
					member.getId(), achievement.getId());

				if (!alreadyAchieved) {
					award(member, achievement);
					// sendNotification();
				}
			}
		}
	}

	private boolean isAchievementMet(final Member member, final Review review,
		final Achievement achievement, final TagCategory[] conditionDetailType) {

		MemberId memberId = new MemberId(member.getOauthId().getOauthServerType(), member.getAccountEmail());

		// 업적 조건 확인
		return switch (achievement.getConditionType()) {
			case REVIEW_COUNT -> memberQueryService.getReviewCount(memberId) >=
				(int)achievement.getConditionValue();

			case TAG_USAGE -> {
				boolean allConditionsMet = false;
				// tag의 경우, category 확인하고 해당 tag에 대한 것만 확인
				for (TagCategory tagCategory : conditionDetailType) {
					if (memberQueryService.getTagCount(memberId, tagCategory) >=
						(int)achievement.getConditionValue()) {
						allConditionsMet = true;
					}
				}
				yield allConditionsMet; // 최종적으로 모든 조건이 만족되었는지 반환
			}
			default -> false;
		};
	}

	private void award(Member member, Achievement achievement) {

		MemberAchievement userAchievement = MemberAchievement.builder()
			.memberId(member.getId())
			.achievementId(achievement.getId())
			.achievedAt(LocalDateTime.now())
			.build();
		memberAchievementRepository.save(userAchievement);
	}
}
