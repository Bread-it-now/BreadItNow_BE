package com.breaditnow.gateway.client.discord;

import java.net.InetSocketAddress;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class DiscordWebHookSender {

	@Value("${webhook.discord.error-url}")
	private String webhookUrl;

	@Value("${webhook.discord.username}")
	private String username;

	@Value("${webhook.discord.avatar-url}")
	private String avatarUrl;

	private final WebClient webClient = WebClient.create();

	public void sendError(Throwable ex, ServerHttpRequest request) {
		String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

		Map<String, Object> embed = new LinkedHashMap<>();
		embed.put("title", "🚨 Gateway 에러 발생");
		embed.put("description", "[" + ex.getClass().getSimpleName() + "] " + ex.getMessage());
		embed.put("color", 0xFF4500);
		embed.put("fields", new Object[] {
			createField("[문제 발생 시각]", timestamp, true),
			createField("[이용자 요청 전체 URL 정보]", safe(request.getURI().toString()), false),
			createField("[이용자 IP 정보]", safe(getClientIp(request)), false),
			createField("[이용자 환경 정보]", safe(request.getHeaders().getFirst("User-Agent")), false),
			createField("[HTTP 헤더 정보]", safe(formatHeaders(request)), true),
			createField("[Parameter 정보]", safe(request.getQueryParams().toSingleValueMap().toString()), false)
		});

		Map<String, Object> payload = new LinkedHashMap<>();
		payload.put("username", username);
		payload.put("avatar_url", avatarUrl);
		payload.put("embeds", new Object[] {embed});

		webClient.post()
			.uri(URI.create(webhookUrl))
			.contentType(MediaType.APPLICATION_JSON)
			.body(BodyInserters.fromValue(payload))
			.retrieve()
			.bodyToMono(Void.class)
			.doOnError(e -> {
				log.warn("❌ Discord webhook 전송 실패. payload: {}", payload);
			})
			.subscribe();
	}

	private Map<String, Object> createField(String name, String value, boolean inline) {
		Map<String, Object> field = new LinkedHashMap<>();
		field.put("name", name);
		field.put("value", safe(value));
		field.put("inline", inline);
		return field;
	}

	private String safe(String value) {
		return value == null || value.isBlank() ? "N/A" : value;
	}

	private String getClientIp(ServerHttpRequest request) {
		InetSocketAddress remoteAddress = request.getRemoteAddress();
		return (remoteAddress != null) ? remoteAddress.getAddress().getHostAddress() : null;
	}

	private String formatHeaders(ServerHttpRequest request) {
		return request.getHeaders().entrySet().stream()
			.map(entry -> "\"" + entry.getKey() + "\":\"" + String.join(",", entry.getValue()) + "\"")
			.collect(Collectors.joining(", "));
	}
}

