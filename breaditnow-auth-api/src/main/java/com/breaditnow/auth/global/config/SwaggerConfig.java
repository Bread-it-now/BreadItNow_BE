package com.breaditnow.auth.global.config;

import static java.util.stream.Collectors.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springdoc.core.customizers.OperationCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.method.HandlerMethod;

import com.breaditnow.auth.global.swagger.annotation.AuthApiErrorCodeExamples;
import com.breaditnow.auth.global.swagger.annotation.CommonErrorCodeExamples;
import com.breaditnow.auth.global.swagger.annotation.DomainErrorCodeExamples;
import com.breaditnow.common.exception.ErrorCode;
import com.breaditnow.common.response.ApiErrorResponse;
import com.breaditnow.common.swagger.ExampleHolder;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {
	@Bean
	public OpenAPI openAPI(@Value("${openapi.service.url}") String url) {
		return new OpenAPI()
			.servers(List.of(new Server().url(url)));
	}

	@Bean
	@Primary
	public OperationCustomizer customize() {
		return (Operation operation, HandlerMethod handlerMethod) -> {
			List<ErrorCode> errorCodes = new ArrayList<>();

			DomainErrorCodeExamples domainErrorCodeExamples = handlerMethod.getMethodAnnotation(
				DomainErrorCodeExamples.class);
			CommonErrorCodeExamples commonErrorCodeExamples = handlerMethod.getMethodAnnotation(
				CommonErrorCodeExamples.class);
			AuthApiErrorCodeExamples authApiErrorCodeExamples = handlerMethod.getMethodAnnotation(
				AuthApiErrorCodeExamples.class);

			if (authApiErrorCodeExamples != null) {
				errorCodes.addAll(Arrays.asList(authApiErrorCodeExamples.value()));
			}
			if (domainErrorCodeExamples != null) {
				errorCodes.addAll(Arrays.asList(domainErrorCodeExamples.value()));
			}
			if (commonErrorCodeExamples != null) {
				errorCodes.addAll(Arrays.asList(commonErrorCodeExamples.value()));
			}

			generateErrorCodeResponseExample(operation, errorCodes);

			return operation;
		};
	}

	private void generateErrorCodeResponseExample(Operation operation, List<ErrorCode> errorCodes) {
		ApiResponses responses = operation.getResponses();
		Map<Integer, List<ExampleHolder>> statusWithExampleHolders = errorCodes.stream()
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
