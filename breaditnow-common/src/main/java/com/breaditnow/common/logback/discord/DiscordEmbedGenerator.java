package com.breaditnow.common.logback.discord;

import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringEscapeUtils;

import com.breaditnow.common.logback.discord.object.EmbedObject;
import com.breaditnow.common.logback.mdc.MDCUtil;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.classic.spi.ThrowableProxyUtil;

public class DiscordEmbedGenerator {

	private static final String APPLICATION_RUN_FAILED = "Application run failed";

	public List<EmbedObject> generateEmbeds(ILoggingEvent event) {
		List<EmbedObject> result = new ArrayList<>();
		result.add(makeMainEmbed(event));

		if (event.getThrowableProxy() != null) {
			result.add(makeExceptionEmbed(event.getThrowableProxy()));
		}
		return result;
	}

	private EmbedObject makeMainEmbed(ILoggingEvent event) {
		Map<String, String> mdc = event.getMDCPropertyMap();

		EmbedObject embed = new EmbedObject()
			.setTitle("🚨 API 에러 발생")
			.setColor(getLevelColor(event))
			.setDescription(getExceptionSummary(event))
			.addField("[문제 발생 시각]", nowString(), false);

		if (mdc.get(MDCUtil.REQUEST_URI_MDC) != null) {
			embed.addField("[" + MDCUtil.REQUEST_FULL_URL_MDC + "]", escape(mdc.get(MDCUtil.REQUEST_FULL_URL_MDC)),
					false)
				.addField("[" + MDCUtil.USER_IP_MDC + "]", escape(mdc.get(MDCUtil.USER_IP_MDC)), false)
				.addField("[" + MDCUtil.USER_AGENT_DETAIL_MDC + "]", escape(mdc.get(MDCUtil.USER_AGENT_DETAIL_MDC)),
					false)
				.addField("[" + MDCUtil.HEADER_MAP_MDC + "]", escapeClean(mdc.get(MDCUtil.HEADER_MAP_MDC)), true)
				.addField("[" + MDCUtil.PARAMETER_MAP_MDC + "]", escapeClean(mdc.get(MDCUtil.PARAMETER_MAP_MDC)),
					false);
		}

		return embed;
	}

	private EmbedObject makeExceptionEmbed(IThrowableProxy throwable) {
		String message = ThrowableProxyUtil.asString(throwable);
		return new EmbedObject()
			.setTitle("[Exception 상세 내용]")
			.setDescription(StringEscapeUtils.escapeJson(message.substring(0, Math.min(message.length(), 4000))));
	}

	private String getExceptionSummary(ILoggingEvent event) {
		if (APPLICATION_RUN_FAILED.equals(event.getFormattedMessage())) {
			return "### " + APPLICATION_RUN_FAILED + "\uFE0F";
		}
		IThrowableProxy t = event.getThrowableProxy();
		if (t != null) {
			return t.getClassName() + ": " + t.getMessage();
		}

		String fallback = event.getFormattedMessage();
		return fallback != null ? fallback : "EXCEPTION 정보가 남지 않았습니다.";
	}

	private Color getLevelColor(ILoggingEvent event) {
		return switch (event.getLevel().levelStr) {
			case "WARN" -> Color.YELLOW;
			case "ERROR" -> Color.RED;
			default -> Color.BLUE;
		};
	}

	private String nowString() {
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}

	private String escape(String value) {
		return value != null ? StringEscapeUtils.escapeJson(value) : "N/A";
	}

	private String escapeClean(String value) {
		if (value == null)
			return "N/A";
		return StringEscapeUtils.escapeJson(value.replaceAll("[\\{\\{\\}]", ""));
	}
}

