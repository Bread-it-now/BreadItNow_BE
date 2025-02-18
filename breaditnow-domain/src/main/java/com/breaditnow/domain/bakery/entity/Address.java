package com.breaditnow.domain.bakery.entity;

import static jakarta.persistence.FetchType.*;
import static lombok.AccessLevel.*;

import com.breaditnow.domain.region.entity.Dong;
import com.breaditnow.domain.region.entity.Gugun;
import com.breaditnow.domain.region.entity.Sido;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Embeddable
@Getter
@Builder
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(access = PROTECTED)
@ToString
public class Address {
	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "sido_code")
	private Sido sido;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "gugun_code")
	private Gugun gugun;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "dong_code")
	private Dong dong;

	private double latitude;

	private double longitude;

	@Column(name = "address_description")
	private String description;
}
