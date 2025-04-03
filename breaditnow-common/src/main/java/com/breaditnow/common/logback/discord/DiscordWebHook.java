package com.breaditnow.common.logback.discord;

import static org.springframework.http.HttpMethod.*;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

public class DiscordWebHook {

	private static final RestTemplate restTemplate = new RestTemplate();

	private final String urlString;

	public DiscordWebHook(String urlString) {
		this.urlString = urlString;
	}

	public void send(String payload) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> entity = new HttpEntity<>(payload, headers);
		try {
			ResponseEntity<String> response = restTemplate.exchange(urlString, POST, entity, String.class);
			if (!response.getStatusCode().is2xxSuccessful()) {
				// 	throw new BreaditnowException(EXTERNAL_API_BAD_REQUEST, "Failed to execute Discord webhook");
			}
		} catch (HttpClientErrorException e) {
			// throw new BreaditnowException(EXTERNAL_API_BAD_REQUEST);
		}
	}
}
