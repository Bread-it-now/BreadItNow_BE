package com.breaditnow.common.logback.discord;

import static com.breaditnow.common.exception.CommonErrorCode.*;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.breaditnow.common.exception.BreaditnowException;
import com.breaditnow.common.util.RestTemplateUtil;

public class DiscordWebHookSender {

	private final String urlString;

	public DiscordWebHookSender(String urlString) {
		this.urlString = urlString;
	}

	public void send(String payload) {
		HttpHeaders headers = RestTemplateUtil.createHeaders(MediaType.APPLICATION_JSON);
		ResponseEntity<String> response = RestTemplateUtil.sendPostRequest(urlString, headers, payload, String.class);

		if (!response.getStatusCode().is2xxSuccessful()) {
			throw new BreaditnowException(EXTERNAL_API_BAD_REQUEST, "Failed to execute Discord webhook");
		}

	}
}
