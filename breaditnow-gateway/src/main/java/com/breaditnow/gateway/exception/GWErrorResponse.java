package com.breaditnow.gateway.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

@Getter
public class GWErrorResponse {
	private String errorMessage;
	private LocalDateTime localDateTime;
	private Map<String, Object> addtionInfos = new HashMap<>();

	public GWErrorResponse(String errorMessage, LocalDateTime localDateTime) {
		this.errorMessage = errorMessage;
		this.localDateTime = localDateTime;
	}

	public static GWErrorResponse defaultBuild(String errorMessage) {
		return new GWErrorResponse(errorMessage, LocalDateTime.now());
	}

}
