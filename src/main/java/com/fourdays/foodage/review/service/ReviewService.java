package com.fourdays.foodage.review.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

import com.fourdays.foodage.member.domain.Member;
import com.fourdays.foodage.member.service.MemberQueryService;
import com.fourdays.foodage.review.domain.*;
import com.fourdays.foodage.review.dto.*;
import com.fourdays.foodage.tag.domain.Tag;
import com.fourdays.foodage.tag.domain.TagRepository;
import org.apache.catalina.core.PropertiesRoleMappingListener;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import com.fourdays.foodage.member.vo.MemberId;
import com.fourdays.foodage.review.domain.model.ReviewModel;
import com.fourdays.foodage.review.domain.model.ReviewModelWithThumbnail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class ReviewService {

	private final ReviewCustomRepository reviewCustomRepository;
	private final ReviewRepository reviewRepository;
	private final ReviewTagRepository reviewTagRepository;
	private final ReviewMenuRepository reviewMenuRepository;
	private final ReviewImageRepository reviewImageRepository;
	private final TagRepository tagRepository;
	private final MemberQueryService memberQueryService;

	public ReviewService(ReviewCustomRepository reviewCustomRepository, ReviewRepository reviewRepository, ReviewTagRepository reviewTagRepository, ReviewMenuRepository reviewMenuRepository, ReviewImageRepository reviewImageRepository, TagRepository tagRepository, MemberQueryService memberQueryService) {
		this.reviewCustomRepository = reviewCustomRepository;
		this.reviewRepository = reviewRepository;
        this.reviewTagRepository = reviewTagRepository;
        this.reviewMenuRepository = reviewMenuRepository;
        this.reviewImageRepository = reviewImageRepository;
        this.tagRepository = tagRepository;
        this.memberQueryService = memberQueryService;
    }

	public ReviewModel getReview(final MemberId memberId,
		final Long reviewId) {

		return reviewCustomRepository.findReviewById(memberId, reviewId);
	}

	public ReviewResponse getReviews(final Long idx, final MemberId memberId,
		final Pageable pageable) {

		int totalCount = reviewCustomRepository.countByReviewId(memberId);

		Slice<Long> reviewIds =
			reviewCustomRepository.findReviewIds(idx, memberId, pageable);

		List<ReviewModelWithThumbnail> reviews =
			reviewCustomRepository.findReviews(reviewIds.getContent(), memberId, pageable);

		return new ReviewResponse(reviewIds, reviews, totalCount);
	}

	public DateReviewResponse getReviewsByDate(final MemberId memberId,
		final LocalDate date) {

		log.debug("# getReviewsByDate() : {}", date);

		List<ReviewModelWithThumbnail> reviewModels =
			reviewCustomRepository.findReviewsByDate(memberId, date);

		return new DateReviewResponse(reviewModels, date);
	}

	public List<RecentReviewResponse> getRecentReviews(final MemberId memberId,
		final int limit) {

		log.debug("# getRecentReviews() limit : {}", limit);

		return reviewCustomRepository.findRecentReviews(memberId, limit);
	}

	public Map<LocalDate, PeriodReviewGroup> getReviewsByPeriod(final MemberId memberId,
		final PeriodReviewRequest request) {

		log.debug("# getReviewsByPeriod() : {} ~ {}", request.getStartDate(),
			request.getEndDate());

		List<PeriodReviewResponse> weeklyReviews =
			reviewCustomRepository.findReviewsByPeriod(memberId, request.getStartDate(),
				request.getEndDate());

		// todo: refactoring
		Map<LocalDate, PeriodReviewGroup> response = weeklyReviews.stream()
			.collect(Collectors.groupingBy(PeriodReviewResponse::getCreatedAt,
				TreeMap::new,
				Collectors.collectingAndThen(
					Collectors.toList(),
					list -> {
						String dayOfWeek = list.get(0).getDayOfWeek();
						// String lastEatenFood = list.get(list.size() - 1).getLastEatenFood();
						int count =
							list.stream()
								.map(PeriodReviewResponse::getId)
								.collect(Collectors.toList()).size();

						return new PeriodReviewGroup(dayOfWeek, count);
					}
				)
			));

		return response;
	}

	@Transactional
	public Review createReview(CreateReviewRequestDto request, MemberId memberId) {
		Member findMember = memberQueryService.findByMemberId(memberId);

		Review review = Review.builder()
				.restaurant(request.getRestaurant())
				.address(request.getAddress())
				.rating(request.getRating())
				.contents(request.getContents())
				.date(request.getDate())
				.creatorId(findMember.getId())
				.build();

		Review savedReview = reviewRepository.save(review);
		Long reviewId = savedReview.getId();

		for (Long tagId : request.getTagIds()) {
			Tag tag = tagRepository.findById(tagId).get();	//optional
			ReviewTag reviewTag = ReviewTag.builder()
					.reviewId(reviewId)
					.tagId(tag.getId())
					.tagName(tag.getName())
					.tagTextColor(tag.getTextColor())
					.tagBgColor(tag.getBgColor())
					.build();
			reviewTagRepository.save(reviewTag);
		}

		for (CreateReviewRequestDto.ReviewMenuModel menu : request.getMenus()) {
			ReviewMenu reviewMenu = ReviewMenu.builder()
					.reviewId(reviewId)
					.menu(menu.getMenu())
					.price(menu.getPrice())
					.sequence(menu.getSequence())
					.build();
			reviewMenuRepository.save(reviewMenu);
		}

		for (CreateReviewRequestDto.ReviewImageModel image : request.getImages()) {
			ReviewImage reviewImage = ReviewImage.builder()
					.reviewId(reviewId)
					.imageUrl(image.getImageUrl())
					.sequence(image.getSequence())
					.isThumbnail(image.getIsThumbnail())
					.build();
			reviewImageRepository.save(reviewImage);
		}

		return review;
	}
}
