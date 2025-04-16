package com.breaditnow.domain.domain.search;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.breaditnow.domain.domain.search.entity.AutoComplete;

public interface AutoCompleteRepository extends JpaRepository<AutoComplete, Long> {
	@Query(
		value = "SELECT * FROM autocomplete WHERE MATCH(name) AGAINST(?1 IN BOOLEAN MODE) ORDER BY search_count DESC LIMIT 10",
		nativeQuery = true)
	List<AutoComplete> findByKeywordMatch(String keyword);
}
