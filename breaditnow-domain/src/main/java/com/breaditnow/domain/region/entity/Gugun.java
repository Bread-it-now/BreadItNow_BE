package com.breaditnow.domain.region.entity;

import static lombok.AccessLevel.*;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Gugun {
	@Id
	private int gugunCode;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sido_code", nullable = false)
	private Sido sido;

	private String gugunName;
}
