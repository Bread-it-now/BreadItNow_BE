package com.breaditnow.customer.common.config;

import com.breaditnow.common.exception.ErrorCode;
import com.breaditnow.common.response.ApiErrorResponse;
import com.breaditnow.common.swagger.ExampleHolder;
import com.breaditnow.customer.common.presentation.swagger.annotation.CommonErrorCodeExamples;
import com.breaditnow.customer.common.presentation.swagger.annotation.CustomerApiErrorCodeExamples;
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
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.method.HandlerMethod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static io.swagger.v3.oas.models.security.SecurityScheme.In.HEADER;
import static io.swagger.v3.oas.models.security.SecurityScheme.Type.HTTP;
import static java.util.stream.Collectors.groupingBy;

@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {
	private final ApplicationContext applicationContext;

	@Bean
	public OpenAPI openAPI(@Value("${openapi.service.url}") String url) {
		SecurityScheme apiKey = new SecurityScheme()
			.type(HTTP)
			.in(HEADER)
			.name("Authorization")
			.scheme("bearer")
			.bearerFormat("JWT");

		SecurityRequirement securityRequirement = new SecurityRequirement().addList("Bearer Token");

		return new OpenAPI()
			.info(new Info()
				.title("Customer API definition")
				.description("Bread It Now")
			)
			.servers(List.of(new Server().url(url)))
			.components(new Components().addSecuritySchemes("Bearer Token", apiKey))
			.addSecurityItem(securityRequirement);
	}

	@Bean
	public ModelResolver modelResolver(ObjectMapper objectMapper) {
		return new ModelResolver(objectMapper);
	}

	@Bean
	@Primary
	public OperationCustomizer customize() {
		return (Operation operation, HandlerMethod handlerMethod) -> {
			List<ErrorCode> errorCodes = new ArrayList<>();

			CustomerApiErrorCodeExamples customerApiErrorCodeExamples = handlerMethod.getMethodAnnotation(
				CustomerApiErrorCodeExamples.class);
			CommonErrorCodeExamples commonErrorCodeExamples = handlerMethod.getMethodAnnotation(
				CommonErrorCodeExamples.class);

			if (customerApiErrorCodeExamples != null) {
				errorCodes.addAll(Arrays.asList(customerApiErrorCodeExamples.value()));
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
