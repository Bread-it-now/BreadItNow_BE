package com.breaditnow.domain.domain.search.entity;

import static jakarta.persistence.GenerationType.*;

import com.breaditnow.domain.domain.search.enumerate.SearchType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Builder;

@Entity
@Table(name = "autocomplete", indexes = {
	@Index(name = "ft_name_idx", columnList = "name")
})
public class AutoComplete {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private SearchType searchType;

	@Column(name = "search_count")
	private Long searchCount;

	@Builder
	public AutoComplete(String name, SearchType searchType, Long searchCount) {
		this.name = name;
		this.searchType = searchType;
		this.searchCount = searchCount;
	}
}
