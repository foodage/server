package com.fourdays.foodage.common.util;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RequestHandler implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
		throws Exception {

		System.out.println();
		// log.debug("==================== [new request] ====================");
		log.debug(">>>>>>>>>>>>>>>>>>>> [new request] >>>>>>>>>>>>>>>>>>>>");
		log.debug("@ Request URI : [{}] {}", request.getMethod(), request.getRequestURI());
		log.debug("@ Client IP (remote) : {}", request.getRemoteAddr());
		log.debug("@ Client IP (parse-header) : {}", getClientIpAddr(request));
		System.out.println();

		return HandlerInterceptor.super.preHandle(request, response, handler);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
		ModelAndView modelAndView) throws Exception {
		// log.debug("======================== [end] ========================");
		log.debug("<<<<<<<<<<<<<<<<<<<<<<<< [end] <<<<<<<<<<<<<<<<<<<<<<<<");
		System.out.println();

		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}

	private String getClientIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}

		return ip;
	}
}
