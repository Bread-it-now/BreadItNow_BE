package com.breaditnow.domain.domain.search.entity;

import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

import com.breaditnow.domain.domain.search.enumerate.SearchType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "autocomplete")
@NoArgsConstructor(access = PROTECTED)
@Getter
public class Autocomplete {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Enumerated(EnumType.STRING)
	@Column(name = "type", nullable = false)
	private SearchType searchType;

	@Column(name = "search_count")
	private Long searchCount;

	@Builder
	public Autocomplete(String name, SearchType searchType, Long searchCount) {
		this.name = name;
		this.searchType = searchType;
		this.searchCount = searchCount;
	}
}
