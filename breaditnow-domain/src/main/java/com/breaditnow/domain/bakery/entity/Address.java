package com.breaditnow.domain.bakery.entity;

import static jakarta.persistence.FetchType.*;
import static lombok.AccessLevel.*;

import com.breaditnow.domain.region.entity.Region;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.OneToOne;
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
	@OneToOne(fetch = LAZY)
	@JoinColumns({
		@JoinColumn(name = "sido_code", referencedColumnName = "sidoCode"),
		@JoinColumn(name = "gugun_code", referencedColumnName = "gugunCode"),
		@JoinColumn(name = "dong_code", referencedColumnName = "dongCode")
	})
	private Region region;

	private double latitude;

	private double longitude;

	@Column(name = "address_description")
	private String description;
}
