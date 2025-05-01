package com.breaditnow.common.util;

import static com.breaditnow.common.exception.CommonErrorCode.*;
import static org.springframework.http.HttpMethod.*;

import java.net.URI;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.breaditnow.common.exception.BreaditnowException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class RestTemplateUtil {

	private static final RestTemplate restTemplate = new RestTemplate();

	public static HttpHeaders createHeaders(MediaType contentType) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(contentType);
		return headers;
	}

	public static HttpHeaders createHeaders(MediaType contentType, String token) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(contentType);
		headers.setBearerAuth(token);
		return headers;
	}

	public static <T> ResponseEntity<T> sendGetRequest(String url, HttpHeaders headers, Class<T> responseType) {
		HttpEntity<String> entity = new HttpEntity<>(headers);
		try {
			return restTemplate.exchange(url, GET, entity, responseType);
		} catch (HttpClientErrorException e) {
			if (e.getStatusCode().is4xxClientError()) {
				return ResponseEntity.status(e.getStatusCode()).build();
			} else {
				throw new BreaditnowException(EXTERNAL_API_BAD_REQUEST);
			}
		}
	}

	public static <T> ResponseEntity<T> sendGetRequestWithUri(URI uri, HttpHeaders headers, Class<T> responseType) {
		HttpEntity<String> entity = new HttpEntity<>(headers);
		try {
			return restTemplate.exchange(uri, GET, entity, responseType);
		} catch (HttpClientErrorException e) {
			if (e.getStatusCode().is4xxClientError()) {
				return ResponseEntity.status(e.getStatusCode()).build();
			} else {
				throw new BreaditnowException(EXTERNAL_API_BAD_REQUEST);
			}
		}
	}

	public static <T> ResponseEntity<T> sendPostRequest(String url, HttpHeaders headers, String body,
		Class<T> responseType) {
		HttpEntity<String> entity = new HttpEntity<>(body, headers);
		try {
			return restTemplate.exchange(url, POST, entity, responseType);
		} catch (HttpClientErrorException e) {
			throw new BreaditnowException(EXTERNAL_API_BAD_REQUEST);
		}
	}

	public static <T> ResponseEntity<T> sendPostRequest(String url, HttpHeaders headers,
		MultiValueMap<String, String> body,
		Class<T> responseType) {
		HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(body, headers);
		try {
			return restTemplate.exchange(url, POST, entity, responseType);
		} catch (HttpClientErrorException e) {
			throw new BreaditnowException(EXTERNAL_API_BAD_REQUEST, e.getResponseBodyAsString());
		} catch (HttpServerErrorException e) {
			throw new BreaditnowException(EXTERNAL_API_INTERNAL_SERVER_ERROR, e.getResponseBodyAsString());
		} catch (Exception e) {
			throw new BreaditnowException(EXTERNAL_API_BAD_REQUEST);
		}
	}
}
