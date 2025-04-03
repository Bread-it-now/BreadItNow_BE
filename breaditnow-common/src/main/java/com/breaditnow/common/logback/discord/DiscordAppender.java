package com.breaditnow.common.logback.discord;

import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringEscapeUtils;

import com.breaditnow.common.logback.discord.object.EmbedObject;
import com.breaditnow.common.logback.discord.object.JsonObject;
import com.breaditnow.common.logback.mdc.MDCUtil;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.classic.spi.ThrowableProxyUtil;
import ch.qos.logback.core.UnsynchronizedAppenderBase;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Setter
@Slf4j
public class DiscordAppender extends UnsynchronizedAppenderBase<ILoggingEvent> {

	private String discordWebhookUrl;
	private String username;
	private String avatarUrl;
	private static final String APPLICATION_RUN_FAILED = "Application run failed";

	public void setDiscordWebhookURL(String discordWebhookURL) {
		this.discordWebhookUrl = discordWebhookURL;
	}

	@Override
	protected void append(ILoggingEvent eventObject) {
		DiscordWebHook discordWebHook = new DiscordWebHook(discordWebhookUrl);
		DiscordEmbedBuilder embedBuilder = new DiscordEmbedBuilder();

		JsonObject jsonObject = new JsonObject();
		jsonObject.put("username", username);
		jsonObject.put("avatar_url", avatarUrl);

		List<EmbedObject> embeds = createEmbeds(eventObject);
		if (!embeds.isEmpty()) {
			JsonObject embedsJson = embedBuilder.createEmbedJson(embeds);
			jsonObject.merge(embedsJson);
		}

		String payload = jsonObject.toString();
		discordWebHook.send(payload);
	}

	private List<EmbedObject> createEmbeds(ILoggingEvent eventObject) {
		List<EmbedObject> embeds = new ArrayList<>();

		EmbedObject mainEmbedObject = makeMainEmbedObject(eventObject);
		embeds.add(mainEmbedObject);

		IThrowableProxy throwable = eventObject.getThrowableProxy();
		if (throwable != null) {
			EmbedObject exceptionEmbedObject = makeExceptionEmbedObject(throwable);
			embeds.add(exceptionEmbedObject);
		}

		return embeds;
	}

	private EmbedObject makeMainEmbedObject(ILoggingEvent eventObject) {
		Map<String, String> mdcPropertyMap = eventObject.getMDCPropertyMap();
		Color messageColor = getLevelColor(eventObject);
		String level = eventObject.getLevel().levelStr;
		String exceptionBrief = extractExceptionBrief(eventObject.getFormattedMessage(),
			eventObject.getThrowableProxy());

		return new EmbedObject()
			.setTitle("\uD83D\uDEA8 에러 발생")
			.setColor(messageColor)
			.setDescription(exceptionBrief)
			.addField("[" + "Exception Level" + "]",
				safeEscape(level),
				true)
			.addField("[문제 발생 시각]",
				LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
				false)
			.addField(
				"[" + MDCUtil.REQUEST_URI_MDC + "]",
				safeEscape(mdcPropertyMap.get(MDCUtil.REQUEST_URI_MDC)),
				false)
			.addField(
				"[" + MDCUtil.USER_IP_MDC + "]",
				safeEscape(mdcPropertyMap.get(MDCUtil.USER_IP_MDC)),
				false)
			.addField(
				"[" + MDCUtil.USER_AGENT_DETAIL_MDC + "]",
				safeEscape(mdcPropertyMap.get(MDCUtil.USER_AGENT_DETAIL_MDC)),
				false)
			.addField(
				"[" + MDCUtil.HEADER_MAP_MDC + "]",
				safeEscapeAndClean(mdcPropertyMap.get(MDCUtil.HEADER_MAP_MDC)),
				true)
			.addField(
				"[" + MDCUtil.PARAMETER_MAP_MDC + "]",
				safeEscapeAndClean(mdcPropertyMap.get(MDCUtil.PARAMETER_MAP_MDC)),
				false);
	}

	private EmbedObject makeExceptionEmbedObject(IThrowableProxy throwable) {
		String exceptionDetail = ThrowableProxyUtil.asString(throwable);
		String exception = exceptionDetail.substring(0, Math.min(exceptionDetail.length(), 4000));
		return new EmbedObject()
			.setTitle("[Exception 상세 내용]")
			.setDescription(StringEscapeUtils.escapeJson(exception));
	}

	private Color getLevelColor(ILoggingEvent eventObject) {
		return switch (eventObject.getLevel().levelStr) {
			case "WARN" -> Color.YELLOW;
			case "ERROR" -> Color.RED;
			default -> Color.BLUE;
		};
	}

	private String extractExceptionBrief(String content, IThrowableProxy throwable) {
		if (content.equals(APPLICATION_RUN_FAILED)) {
			return "### " + APPLICATION_RUN_FAILED + "\uFE0F";
		}
		if (throwable != null) {
			return throwable.getClassName() + ": " + throwable.getMessage();
		}
		return "EXCEPTION 정보가 남지 않았습니다.";
	}

	private String safeEscape(String value) {
		return value != null ? StringEscapeUtils.escapeJson(value) : "N/A";
	}

	private String safeEscapeAndClean(String value) {
		if (value == null) {
			return "N/A";
		}
		return StringEscapeUtils.escapeJson(value.replaceAll("[\\{\\{\\}]", ""));
	}
}
