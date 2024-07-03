package com.fourdays.foodage.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fourdays.foodage.common.enums.converter.ReviewViewTypeConverter;
import com.fourdays.foodage.common.util.RequestHandler;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Value("${application.client.base-url}")
	private String clientBaseUrl;

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		// 모든 경로에 대해 모든 HTTP 메서드에 대한 CORS를 허용
		registry.addMapping("/**")
			.allowedOrigins(clientBaseUrl)
			.allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE")
			.allowCredentials(true) // use cookie
			.allowedHeaders("*");
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new RequestHandler())
			.excludePathPatterns("/css/**", "/images/**", "/js/**");
	}

	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(new ReviewViewTypeConverter());
	}
}
