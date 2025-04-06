package com.breaditnow.common.logback.discord;

import java.util.List;

import com.breaditnow.common.logback.discord.object.EmbedObject;
import com.breaditnow.common.logback.discord.object.JsonObject;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.UnsynchronizedAppenderBase;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Setter
@Slf4j
public class DiscordAppender extends UnsynchronizedAppenderBase<ILoggingEvent> {

	private String discordWebhookUrl;
	private String username;
	private String avatarUrl;

	private final DiscordEmbedBuilder embedBuilder = new DiscordEmbedBuilder();
	private final DiscordEmbedGenerator discordEmbedGenerator = new DiscordEmbedGenerator();

	public void setDiscordWebhookURL(String discordWebhookURL) {
		this.discordWebhookUrl = discordWebhookURL;
	}

	@Override
	protected void append(ILoggingEvent eventObject) {
		DiscordWebHookSender discordWebHookSender = new DiscordWebHookSender(discordWebhookUrl);

		String payload = createPayload(eventObject, username, avatarUrl);

		discordWebHookSender.send(payload);
	}

	public String createPayload(ILoggingEvent event, String username, String avatarUrl) {
		JsonObject root = new JsonObject();
		root.put("username", username);
		root.put("avatar_url", avatarUrl);

		List<EmbedObject> embeds = discordEmbedGenerator.generateEmbeds(event);
		if (!embeds.isEmpty()) {
			root.merge(embedBuilder.createEmbedJson(embeds));
		}
		return root.toString();
	}
}
