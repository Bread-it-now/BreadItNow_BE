package com.breaditnow.common.util;

import static com.breaditnow.common.exception.CommonErrorCode.*;

import java.io.IOException;

import com.breaditnow.common.exception.BreaditnowException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;

public class JsonUtil {

	private static final ObjectMapper mapper = new ObjectMapper();

	public static <T> T readValue(HttpServletRequest request, Class<T> type) {
		try {
			return mapper.readValue(request.getInputStream(), type);
		} catch (IOException e) {
			throw new BreaditnowException(JSON_DESERIALIZATION_ERROR, e.getMessage());
		}
	}

	public static <T> T fromJson(String json, TypeReference<T> typeRef) {
		try {
			return mapper.readValue(json, typeRef);
		} catch (JsonProcessingException e) {
			throw new BreaditnowException(JSON_DESERIALIZATION_ERROR, e.getMessage());
		}
	}

	public static String toJson(Object object) {
		try {
			return mapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			throw new BreaditnowException(JSON_SERIALIZATION_ERROR, e.getMessage());
		}
	}

}
