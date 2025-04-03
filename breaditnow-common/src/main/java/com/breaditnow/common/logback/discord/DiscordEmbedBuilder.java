package com.breaditnow.common.logback.discord;

import static com.breaditnow.common.exception.CommonErrorCode.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import com.breaditnow.common.exception.BreaditnowException;
import com.breaditnow.common.logback.discord.object.EmbedObject;
import com.breaditnow.common.logback.discord.object.Field;
import com.breaditnow.common.logback.discord.object.JsonObject;

public class DiscordEmbedBuilder {

	public JsonObject createEmbedJson(List<EmbedObject> embeds) {
		if (embeds.isEmpty()) {
			throw new BreaditnowException(CREATE_DISCORD_APPEND_MESSAGE_FAILURE);
		}

		List<JsonObject> embedObjects = new ArrayList<>();

		for (EmbedObject embed : embeds) {
			JsonObject jsonEmbed = new JsonObject();

			jsonEmbed.put("title", embed.getTitle());
			jsonEmbed.put("description", embed.getDescription());

			processColor(embed, jsonEmbed);
			processFields(embed.getFields(), jsonEmbed);

			embedObjects.add(jsonEmbed);
		}

		JsonObject result = new JsonObject();
		result.put("embeds", embedObjects.toArray());
		return result;
	}

	private void processColor(EmbedObject embed, JsonObject jsonEmbed) {
		if (embed.getColor() != null) {
			Color color = embed.getColor();
			int rgb = color.getRed();
			rgb = (rgb << 8) + color.getGreen();
			rgb = (rgb << 8) + color.getBlue();

			jsonEmbed.put("color", rgb);
		}
	}

	private void processFields(List<Field> fields, JsonObject jsonEmbed) {
		List<JsonObject> jsonFields = new ArrayList<>();

		for (Field field : fields) {
			JsonObject jsonField = new JsonObject();

			jsonField.put("name", field.getName());
			jsonField.put("value", field.getValue());
			jsonField.put("inline", field.isInline());

			jsonFields.add(jsonField);
		}

		jsonEmbed.put("fields", jsonFields.toArray());
	}
}
