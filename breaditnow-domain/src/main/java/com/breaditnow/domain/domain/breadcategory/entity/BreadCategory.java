package com.breaditnow.domain.domain.breadcategory.entity;

import static lombok.AccessLevel.*;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class BreadCategory {
	@Id
	private Long id;

	private String name;
}
