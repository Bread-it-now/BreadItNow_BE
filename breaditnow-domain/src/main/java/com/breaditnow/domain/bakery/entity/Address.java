package com.breaditnow.domain.bakery.entity;

import static lombok.AccessLevel.*;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Embeddable
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@ToString
public class Address {
	private String city;

	private String region;

	private String zipcode;

	private double latitude;

	private double longitude;

	private String description;
}
