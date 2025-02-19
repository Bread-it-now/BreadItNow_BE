package com.breaditnow.domain.region.entity;

import static lombok.AccessLevel.*;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PRIVATE)
public class Region {
	@EmbeddedId
	private RegionPK id;

	private String sidoName;
	private String gugunName;

	@Data
	@Embeddable
	@NoArgsConstructor(access = PROTECTED)
	@AllArgsConstructor
	static class RegionPK implements Serializable {
		private int sidoCode;

		private int gugunCode;
	}
}
