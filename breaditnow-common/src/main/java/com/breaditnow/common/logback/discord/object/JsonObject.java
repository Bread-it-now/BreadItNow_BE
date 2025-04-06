package com.breaditnow.common.logback.discord.object;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class JsonObject {

	private final HashMap<String, Object> sendDiscordMessageMap = new HashMap<>();

	public void put(String key, Object value) {
		if (value != null) {
			sendDiscordMessageMap.put(key, value);
		}
	}

	public void merge(JsonObject other) {
		if (other == null) {
			return;
		}

		for (Map.Entry<String, Object> entry : other.sendDiscordMessageMap.entrySet()) {
			this.sendDiscordMessageMap.put(entry.getKey(), entry.getValue());
		}
	}

	/**
	 * Discord에 보낼 Log Back Message에 불필요한 내용 등을 정리하기 위한 메서드
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		Set<Map.Entry<String, Object>> entrySet = sendDiscordMessageMap.entrySet();
		builder.append("{");

		int iter = 0;
		for (Map.Entry<String, Object> entry : entrySet) {
			Object val = entry.getValue();
			builder.append(quote(entry.getKey())).append(":");

			if (val instanceof String) {
				builder.append(quote(String.valueOf(val)));
			} else if (val instanceof Integer) {
				builder.append(Integer.valueOf(String.valueOf(val)));
			} else if (val instanceof Boolean) {
				builder.append(val);
			} else if (val instanceof JsonObject) {
				builder.append(val);
			} else if (val.getClass().isArray()) {
				builder.append("[");
				int len = Array.getLength(val);
				for (int j = 0; j < len; j++) {
					builder.append(Array.get(val, j).toString()).append(j != len - 1 ? "," : "");
				}
				builder.append("]");
			}

			builder.append(++iter == entrySet.size() ? "}" : ",");
		}

		return builder.toString();
	}

	private String quote(String string) {
		return "\"" + string + "\"";
	}
}
