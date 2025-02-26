package com.breaditnow.domain.domain.bakery.entity;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

import com.breaditnow.domain.domain.region.entity.Region;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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
@Entity
public class Address {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

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

	public void update(Region region, String description) {
		this.region = region;
		this.description = description;
	}
}
