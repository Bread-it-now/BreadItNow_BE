package com.breaditnow.gateway.exception;

import static com.breaditnow.gateway.exception.GatewayErrorCode.*;

import java.nio.charset.StandardCharsets;

import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;

import com.breaditnow.gateway.client.discord.DiscordWebHookSender;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Order(-1)
@RequiredArgsConstructor
public class GlobalExceptionHandler implements ErrorWebExceptionHandler {
	private final ObjectMapper objectMapper;
	private final DiscordWebHookSender discordWebHookSender;

	@Override
	public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
		ServerHttpRequest request = exchange.getRequest();
		ServerHttpResponse response = exchange.getResponse();

		if (response.isCommitted()) {
			return Mono.error(ex);
		}

		GWErrorResponse gwErrorResponse;
		HttpStatus status;
		if (ex instanceof GatewayException gatewayException) {
			gwErrorResponse = GWErrorResponse.of(gatewayException.getErrorCode());
			status = gatewayException.getErrorCode().getHttpStatus();

			log.error("[{}}] code={}, message={}",
				gatewayException.getClass().getName(),
				gatewayException.getErrorCode().name(),
				gatewayException.getMessage()
			);
		} else {
			gwErrorResponse = GWErrorResponse.of(UNDEFINED_ERROR, ex.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;

			log.error("[{}] message={}", ex.getClass().getName(), ex.getMessage(), ex);
		}

		discordWebHookSender.sendError(ex, exchange.getRequest());

		response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
		response.setStatusCode(status);

		byte[] errorResponse;
		try {
			errorResponse = objectMapper.writeValueAsBytes(gwErrorResponse);
		} catch (JsonProcessingException e) {
			String fallbackError = "{\"status\":\"ERROR\","
				+ "\"code\":\"JSON_PROCESSING_ERROR\","
				+ "\"message\":\"JSON 응답 직렬화에 실패하였습니다.\"}";
			errorResponse = fallbackError.getBytes(StandardCharsets.UTF_8);
		}

		DataBufferFactory bufferFactory = response.bufferFactory();
		DataBuffer buffer = bufferFactory.wrap(errorResponse);
		return exchange.getResponse().writeWith(Flux.just(buffer));
	}

}
