package com.breaditnow.external.global.config;

import static io.swagger.v3.oas.models.security.SecurityScheme.Type.*;
import static java.util.stream.Collectors.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springdoc.core.customizers.OperationCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.method.HandlerMethod;

import com.breaditnow.common.exception.ErrorCode;
import com.breaditnow.common.response.ApiErrorResponse;
import com.breaditnow.external.domain.swagger.ApiErrorCodeExample;
import com.breaditnow.external.domain.swagger.ExampleHolder;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.v3.core.jackson.ModelResolver;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {
	private final ApplicationContext applicationContext;

	@Bean
	public OpenAPI openAPI() {
		String jwt = "JWT";
		SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwt);
		Components components = new Components().addSecuritySchemes(jwt, new SecurityScheme()
			.name(jwt)
			.type(HTTP)
			.scheme("bearer")
			.bearerFormat(jwt)
		);
		return new OpenAPI()
			.info(apiInfo())
			.addSecurityItem(securityRequirement)
			.components(components);
	}

	private Info apiInfo() {
		return new Info()
			.title("API Test") // API의 제목
			.description("Let's practice Swagger UI") // API에 대한 설명
			.version("1.0.0"); // API의 버전
	}

	@Bean
	public ModelResolver modelResolver(ObjectMapper objectMapper) {
		return new ModelResolver(objectMapper);
	}

	@Bean
	@Primary
	public OperationCustomizer customize() {
		return (Operation operation, HandlerMethod handlerMethod) -> {
			ApiErrorCodeExample apiErrorCodeExample = handlerMethod.getMethodAnnotation(ApiErrorCodeExample.class);
			if (apiErrorCodeExample != null) {
				generateErrorCodeResponseExample(operation, apiErrorCodeExample.value());
			}
			return operation;
		};
	}

	private void generateErrorCodeResponseExample(Operation operation, Class<? extends ErrorCode> type) {
		ApiResponses responses = operation.getResponses();

		ErrorCode[] errorCodes = type.getEnumConstants();

		Map<Integer, List<ExampleHolder>> statusWithExampleHolders = Arrays.stream(errorCodes)
			.map(errorCode -> ExampleHolder.builder()
				.holder(getSwaggerExample(errorCode))
				.code(errorCode.getHttpStatus().value())
				.name(errorCode.getMessage())
				.build())
			.collect(groupingBy(ExampleHolder::code));

		addExamplesToResponses(responses, statusWithExampleHolders);
	}

	private Example getSwaggerExample(ErrorCode errorCode) {
		Example example = new Example();
		example.setValue(ApiErrorResponse.of(errorCode));
		return example;
	}

	private void addExamplesToResponses(ApiResponses responses,
		Map<Integer, List<ExampleHolder>> statusWithExampleHolders) {
		statusWithExampleHolders.forEach(
			(status, v) -> {
				Content content = new Content();
				MediaType mediaType = new MediaType();
				ApiResponse apiResponse = new ApiResponse();
				v.forEach(exampleHolder -> mediaType.addExamples(exampleHolder.name(), exampleHolder.holder()));
				content.addMediaType("application/json", mediaType);
				apiResponse.setContent(content);
				responses.addApiResponse(status.toString(), apiResponse);
			}
		);
	}

	@Bean
	public GroupedOpenApi allApi(OperationCustomizer operationCustomizer) {
		return GroupedOpenApi.builder()
			.group("all")
			.pathsToMatch("/api/**")
			.addOperationCustomizer(operationCustomizer)
			.build();
	}
}
