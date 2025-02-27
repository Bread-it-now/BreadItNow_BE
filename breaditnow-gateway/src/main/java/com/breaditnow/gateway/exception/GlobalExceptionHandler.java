package com.breaditnow.gateway.exception;

import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Order(-1)
@RequiredArgsConstructor
public class GlobalExceptionHandler implements ErrorWebExceptionHandler {
	private final ObjectMapper objectMapper;

	@Override
	public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
		ServerHttpResponse response = exchange.getResponse();

		if (response.isCommitted()) {
			return Mono.error(ex);
		}

		//header
		response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

		if (ex instanceof ResponseStatusException) {
			response.setStatusCode(((ResponseStatusException)ex).getStatusCode());
		}

		return response
			.writeWith(Mono.fromSupplier(() -> {
				DataBufferFactory bufferFactory = response.bufferFactory();
				try {
					GWErrorResponse gwErrorResponse = GWErrorResponse.defaultBuild(ex.getMessage());
					byte[] errorResponse = objectMapper.writeValueAsBytes(gwErrorResponse);
					return bufferFactory.wrap(errorResponse);
				} catch (Exception e) {
					log.error("error", e);
					return bufferFactory.wrap(new byte[0]);
				}
			}));
	}
}
