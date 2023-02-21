package com.project.toy.common.config;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class Swagger2Config {

	@Bean
	public GroupedOpenApi publicApi() {
		return GroupedOpenApi.builder()
				.group("v1-definition")
				.pathsToMatch("/**")
				.build();
	}
	
	@Bean
	public OpenAPI springShopOpenApi() {
		return new OpenAPI()
				.info(new Info().title("OYEZ API")
					.description("OYEZ API 명세서입니다.")
					.version("v0.0.1"));
	}
}
