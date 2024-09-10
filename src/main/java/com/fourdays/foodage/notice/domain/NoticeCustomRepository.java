package com.fourdays.foodage.notice.domain;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import com.fourdays.foodage.notice.domain.model.NoticeModel;

public interface NoticeCustomRepository {

	NoticeModel findNoticeDetailById(final Long noticeId);

	int countByNoticeId();

	Slice<Long> findNoticeIds(final Long idx, final Pageable pageable);

	List<NoticeModel> findNotices(final List<Long> ids, final Pageable pageable);
}
