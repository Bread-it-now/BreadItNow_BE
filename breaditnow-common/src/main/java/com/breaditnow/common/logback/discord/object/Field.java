package com.breaditnow.common.logback.discord.object;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Field {
	private final String name;
	private final String value;
	private final boolean inline;
}
