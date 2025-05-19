package com.breaditnow.domain.domain.breadcategory.entity;

import static lombok.AccessLevel.*;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@Table(name = "P_BreadCategory")
public class BreadCategory {
	@Id
	private Long id;

	private String name;
}
