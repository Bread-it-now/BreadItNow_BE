package com.breaditnow.domain.domain.region.entity;

import static lombok.AccessLevel.*;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PRIVATE)
@Builder
@Table(name = "P_Region")
public class Region {
	@EmbeddedId
	private RegionPK id;

	private String sidoName;
	private String gugunName;
	private String dongName;
}
