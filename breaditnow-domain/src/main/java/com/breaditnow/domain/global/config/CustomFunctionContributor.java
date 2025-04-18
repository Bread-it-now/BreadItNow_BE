package com.breaditnow.domain.global.config;

import org.hibernate.boot.model.FunctionContributions;
import org.hibernate.boot.model.FunctionContributor;
import org.hibernate.type.StandardBasicTypes;

public class CustomFunctionContributor implements FunctionContributor {
	@Override
	public void contributeFunctions(FunctionContributions functionContributions) {
		functionContributions.getFunctionRegistry().registerPattern(
			"match_bm",
			"MATCH(?1) AGAINST (?2 IN BOOLEAN MODE)",
			functionContributions
				.getTypeConfiguration()
				.getBasicTypeRegistry()
				.resolve(StandardBasicTypes.DOUBLE)
		);
	}
}
