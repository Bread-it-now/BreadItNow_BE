package com.breaditnow.domain.region.entity;

import static lombok.AccessLevel.*;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Sido {
	@Id
	private int sidoCode;

	private String sidoName;

	@OneToMany(mappedBy = "sido", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Gugun> guguns = new ArrayList<>();
}
