package com.breaditnow.external.domain.swagger;

import io.swagger.v3.oas.models.examples.Example;
import lombok.Builder;

@Builder
public record ExampleHolder(
	Example example,
	String exampleName,
	int httpStatusValue
) {
}
