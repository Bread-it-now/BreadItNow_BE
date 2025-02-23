package com.breaditnow.domain.region.entity;

import static lombok.AccessLevel.*;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = PROTECTED)
public class RegionPK implements Serializable {
	private int sidoCode;
	private int gugunCode;
	private int dongCode;

	public RegionPK(String addressCode) {
		this.sidoCode = Integer.parseInt(addressCode.substring(0, 2));
		this.gugunCode = Integer.parseInt(addressCode.substring(2, 5));
		this.dongCode = Integer.parseInt(addressCode.substring(5, 8));
	}
}
