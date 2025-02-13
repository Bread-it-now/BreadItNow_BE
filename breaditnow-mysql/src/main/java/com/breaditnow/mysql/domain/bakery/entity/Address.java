package com.breaditnow.mysql.domain.bakery.entity;

import static lombok.AccessLevel.*;

import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Embeddable
@Getter
@NoArgsConstructor(access = PROTECTED)
@ToString
public class Address {
	private String city; // 시/도 (예: 서울특별시, 경기도, 부산광역시 등)

	private String region; // 구/군/시 (예: 강남구, 수원시, 동래구, 홍성군 등)

	private String zipcode; // 우편 번호;

	private double latitude; // 위도

	private double longitude; // 경도

	private String description; // 상세 주소

	@Builder(builderMethodName = "createAddressBuilder")
	public Address(String city, String region, String zipcode, double latitude, double longitude, String description) {
		this.city = city;
		this.region = region;
		this.zipcode = zipcode;
		this.latitude = latitude;
		this.longitude = longitude;
		this.description = description;
	}
}
