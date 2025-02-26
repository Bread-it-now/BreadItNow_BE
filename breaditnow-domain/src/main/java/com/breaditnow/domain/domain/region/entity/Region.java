package com.breaditnow.domain.domain.region.entity;

import static lombok.AccessLevel.*;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PRIVATE)
@Builder
public class Region {
	@EmbeddedId
	private RegionPK id;

	private String sidoName;
	private String gugunName;
	private String dongName;
}
