package com.fourdays.foodage.common.config;// Java

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

	@Value("${application.version}")
	private String version;

	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI()
			.components(new Components())
			.info(apiInfo());
	}

	private Info apiInfo() {
		// markdown 문법 사용
		String description =
			"<h3> Foodage API Doc </h3>"
				+ "[ use port : ``12816`` ]</br>"
				+ "[ base url : ``/api`` ] ";

		return new Info()
			.title("Foodage")
			.description(description)
			.version(version);
	}
}
