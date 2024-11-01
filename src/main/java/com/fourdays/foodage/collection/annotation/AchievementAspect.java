package com.fourdays.foodage.collection.annotation;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.fourdays.foodage.collection.domain.ConditionType;
import com.fourdays.foodage.collection.service.AchievementService;
import com.fourdays.foodage.tag.domain.TagCategory;

@Component
@Aspect
public class AchievementAspect {

	private final AchievementService achievementService;

	public AchievementAspect(AchievementService achievementService) {
		this.achievementService = achievementService;
	}

	@Pointcut("@annotation(achievementTrigger)")
	public void achievementTriggerPointcut(AchievementTrigger achievementTrigger) {
	}

	@AfterReturning(pointcut = "achievementTriggerPointcut(achievementTrigger)",
		argNames = "joinPoint, achievementTrigger")
	public void afterAchievementTrigger(JoinPoint joinPoint, AchievementTrigger achievementTrigger) {

		Object[] args = joinPoint.getArgs();
		ConditionType conditionType = achievementTrigger.conditionType();
		TagCategory[] conditionDetailType = achievementTrigger.conditionDetailType();
		String conditionValue = achievementTrigger.conditionValue();

		achievementService.checkAndAssignAchievement(args, conditionType,
			conditionDetailType, conditionValue);
	}
}
