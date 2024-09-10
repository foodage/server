package com.fourdays.foodage.notice.domain;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * author         : 이름 (ex. ebkim) <br/>
 * date           : 23-12-25 <br/>
 * description    : 설명  <br/>
 */
public interface NoticeRepository extends JpaRepository<Notice, Long> {

}
