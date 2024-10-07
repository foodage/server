package com.fourdays.foodage.common.service;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.fourdays.foodage.common.exception.ExceptionInfo;
import com.fourdays.foodage.common.exception.SendMailException;
import com.fourdays.foodage.inquiry.dto.InquiryAnswerForm;
import com.fourdays.foodage.inquiry.dto.InquiryForm;

import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(readOnly = true)
@Slf4j
public class MailService {

	private final JavaMailSender javaMailSender;

	private final SpringTemplateEngine engine;

	@Value("${application.client.base-url}")
	private String client;

	@Value("${application.mail.sender}")
	private String sender;

	public MailService(JavaMailSender javaMailSender, SpringTemplateEngine engine) {
		this.javaMailSender = javaMailSender;
		this.engine = engine;
	}

	@Async
	public void sendInquiryNotiMail(final String title, final String contents) {

		sendInquiryNotiMail(InquiryForm.builder()
			.title(title)
			.contents(contents)
			.build());
	}

	@Async
	public void sendInquiryNotiMail(final InquiryForm form) {

		log.info("\nsend mail start ----->>>");

		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		try {
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
			mimeMessageHelper.setSubject("[Foodage] 1:1 문의 등록 - " + form.getTitle()); // 제목
			mimeMessageHelper.setFrom(sender);
			mimeMessageHelper.setTo(sender); // 수신인
			mimeMessageHelper.setText(setContext(form), true); // 본문
			javaMailSender.send(mimeMessage);

			log.debug("send() succeeded");

		} catch (Exception e) {
			log.error("send() Exception : {}", e.getMessage());
			throw new SendMailException(ExceptionInfo.ERR_INQUIRY_SEND_MAIL);
		}
		log.info("\n<<<----- send mail finished");
	}

	private String setContext(final InquiryForm form) {

		Context context = new Context(Locale.getDefault());
		List<String> contentsLine = Arrays.asList(form.getContents().split("\\n"));

		context.setVariable("title", form.getTitle());
		context.setVariable("contents", contentsLine);

		return engine.process("1on1-inquiry.html", context);
	}

	//////////////////////////////////////////////////////////////////

	@Async
	public void sendAnswerNotiMail(final long id, final String notifyEmail, final String title,
		final String answer, final boolean isMemberInquiry) {

		String redirectUrl = isMemberInquiry
			? client + "/inquiry/" + id // 회원의 경우 redirect
			: null; // 비회원의 경우 redirect X

		sendAnswerNotiMail(InquiryAnswerForm.builder()
			.notifyEmail(notifyEmail)
			.title(title)
			.contents(answer)
			.redirectUrl(redirectUrl)
			.build());
	}

	@Async
	public void sendAnswerNotiMail(final InquiryAnswerForm form) {

		log.info("\nsend mail start ----->>>");

		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		try {
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
			mimeMessageHelper.setSubject("[Foodage] 1:1 문의 답변이 등록되었습니다."); // 제목
			mimeMessageHelper.setFrom(sender);
			mimeMessageHelper.setTo(form.getNotifyEmail()); // 수신인
			mimeMessageHelper.setText(setContext(form), true); // 본문
			javaMailSender.send(mimeMessage);

			log.debug("send() succeeded to recipient: {}", form.getNotifyEmail());

		} catch (Exception e) {
			log.error("send() Exception : {}", e.getMessage());
			throw new SendMailException(ExceptionInfo.ERR_INQUIRY_SEND_MAIL);
		}
		log.info("\n<<<----- send mail finished");
	}

	private String setContext(final InquiryAnswerForm form) {

		Context context = new Context(Locale.getDefault());
		List<String> answerLine = Arrays.asList(form.getContents().split("\\n"));

		context.setVariable("title", form.getTitle());
		context.setVariable("answerLine", answerLine);
		context.setVariable("redirectUrl", form.getRedirectUrl());

		return engine.process("1on1-inquiry-answer.html", context);
	}
}
