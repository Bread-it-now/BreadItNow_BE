package com.breaditnow.common.logback.discord.object;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class EmbedObject {
	/**
	 * Discord Embed Message 제목
	 */
	private String title;

	/**
	 * Discord Embed Message 상세 내용
	 */
	private String description;

	/**
	 * Discord Embed Message 겉 색깔
	 */
	private Color color;

	/**
	 * Discord Embed Message에 들어갈 Message 내용 List
	 */
	private final List<Field> fields = new ArrayList<>();

	public String getTitle() {
		return title;
	}

	public EmbedObject setTitle(String title) {
		this.title = title;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public EmbedObject setDescription(String description) {
		this.description = description;
		return this;
	}

	public Color getColor() {
		return color;
	}

	public EmbedObject setColor(Color color) {
		this.color = color;
		return this;
	}

	public List<Field> getFields() {
		return fields;
	}

	public EmbedObject addField(String name, String value, boolean inline) {
		this.fields.add(new Field(name, value, inline));
		return this;
	}
}
