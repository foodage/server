package com.fourdays.foodage.common.config;// Java

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
		String description =
			"## Foodage API Docs\n" +
				"``Hello!``\n";

		return new Info()
			.title("Foodage")
			.description(description)
			.version(version);
	}
}
