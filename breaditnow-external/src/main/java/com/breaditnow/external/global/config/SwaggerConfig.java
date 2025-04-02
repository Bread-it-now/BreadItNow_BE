package com.breaditnow.external.global.config;

import static io.swagger.v3.oas.models.security.SecurityScheme.Type.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springdoc.core.customizers.OperationCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.method.HandlerMethod;

import com.breaditnow.common.exception.ErrorCode;
import com.breaditnow.common.response.ApiErrorResponse;
import com.breaditnow.external.domain.swagger.ApiErrorBaseExample;
import com.breaditnow.external.domain.swagger.ApiErrorBaseExamples;
import com.breaditnow.external.domain.swagger.ExampleHolder;

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

@Configuration
public class SwaggerConfig {
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
			.components(new Components())
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
	@Primary
	public OperationCustomizer customize() {
		return (Operation operation, HandlerMethod handlerMethod) -> {
			// (1) 여러 개의 에러 코드를 지정한 @ApiErrorBaseExamples
			ApiErrorBaseExamples multipleExamples = handlerMethod.getMethodAnnotation(ApiErrorBaseExamples.class);
			if (multipleExamples != null) {
				generateErrorBaseResponses(operation, multipleExamples.value());
			} else {
				// (2) 단일 에러 코드를 지정한 @ApiErrorBaseExample
				ApiErrorBaseExample singleExample = handlerMethod.getMethodAnnotation(ApiErrorBaseExample.class);
				if (singleExample != null) {
					generateErrorBaseResponse(operation, singleExample);
				}
			}
			return operation;
		};
	}

	/**
	 * 여러 에러 예시를 한 번에 등록
	 */
	private void generateErrorBaseResponses(Operation operation, ApiErrorBaseExample[] annotations) {
		ApiResponses responses = operation.getResponses();

		Map<Integer, List<ExampleHolder>> statusMap = Arrays.stream(annotations)
			.map(this::createExampleHolderFromAnnotation)
			.collect(Collectors.groupingBy(ExampleHolder::httpStatusValue));

		addExamplesToResponses(responses, statusMap);
	}

	/**
	 * 단일 에러 예시 등록
	 */
	private void generateErrorBaseResponse(Operation operation, ApiErrorBaseExample annotation) {
		ApiResponses responses = operation.getResponses();

		ExampleHolder holder = createExampleHolderFromAnnotation(annotation);
		addExamplesToResponses(responses, holder);
	}

	private ExampleHolder createExampleHolderFromAnnotation(ApiErrorBaseExample annotation) {
		ErrorCode errorCode = parseErrorCode(annotation.codeClass(), annotation.codeConstant());

		Example swaggerExample = new Example();
		swaggerExample.setValue(ApiErrorResponse.of(errorCode));

		return ExampleHolder.builder()
			.httpStatusValue(errorCode.getHttpStatus().value())
			.exampleName(errorCode.getMessage())
			.example(swaggerExample)
			.build();
	}

	private <E extends Enum<E> & ErrorCode> E parseErrorCode(
		Class<? extends Enum<? extends ErrorCode>> codeClass,
		String codeConstant
	) {
		return Enum.valueOf((Class<E>)codeClass, codeConstant);
	}

	private void addExamplesToResponses(ApiResponses responses,
		Map<Integer, List<ExampleHolder>> statusWithExampleHolders) {
		statusWithExampleHolders.forEach((status, holders) -> {
			Content content = new Content();
			MediaType mediaType = new MediaType();
			ApiResponse apiResponse = new ApiResponse();

			holders.forEach(
				holder -> mediaType.addExamples(holder.exampleName(), holder.example())
			);
			content.addMediaType("application/json", mediaType);
			apiResponse.setContent(content);

			responses.addApiResponse(String.valueOf(status), apiResponse);
		});
	}

	private void addExamplesToResponses(ApiResponses responses, ExampleHolder holder) {
		Content content = new Content();
		MediaType mediaType = new MediaType();
		ApiResponse apiResponse = new ApiResponse();

		mediaType.addExamples(holder.exampleName(), holder.example());
		content.addMediaType("application/json", mediaType);
		apiResponse.setContent(content);

		responses.addApiResponse(String.valueOf(holder.httpStatusValue()), apiResponse);
	}

	@Bean
	public GroupedOpenApi allApi() {
		return GroupedOpenApi.builder()
			.group("all")
			.pathsToMatch("/**")
			.build();
	}

	// @Bean
	// public GroupedOpenApi bakeryApi(OperationCustomizer operationCustomizer) {
	// 	return GroupedOpenApi.builder()
	// 		.group("bakery")
	// 		.pathsToMatch("/api/v1/bakery/**")
	// 		.addOperationCustomizer(operationCustomizer)
	// 		.build();
	// }
}
