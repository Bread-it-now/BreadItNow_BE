package com.breaditnow.domain.domain.bakery.entity;

import static lombok.AccessLevel.*;

import com.breaditnow.domain.domain.region.entity.RegionPK;

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
	
	public Address(RegionPK regionPK, String description) {
		this.sidoCode = regionPK.getSidoCode();
		this.gugunCode = regionPK.getGugunCode();
		this.dongCode = regionPK.getDongCode();
		this.description = description;
	}
}
