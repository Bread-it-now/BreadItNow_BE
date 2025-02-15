package com.breaditnow.domain.region.entity;

import static lombok.AccessLevel.*;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Sido {
	@Id
	private int sidoCode;

	private String sidoName;
}
