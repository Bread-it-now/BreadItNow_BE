package com.breaditnow.domain.domain.bakery.entity;

import static lombok.AccessLevel.*;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(access = PROTECTED)
@ToString
public class Address {
	private String sidoCode;
	private String gugunCode;
	private String dongCode;

	private double latitude;

	private double longitude;

	@Column(name = "address_description")
	private String description;
}
