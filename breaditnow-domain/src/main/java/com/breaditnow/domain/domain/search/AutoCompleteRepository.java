package com.breaditnow.domain.domain.search;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.breaditnow.domain.domain.search.entity.AutoComplete;

public interface AutoCompleteRepository extends JpaRepository<AutoComplete, Long> {
	@Query(
		value = "SELECT * FROM autocomplete WHERE MATCH(name) AGAINST(?1 IN BOOLEAN MODE) ORDER BY search_count DESC LIMIT 10",
		nativeQuery = true)
	List<AutoComplete> findByKeywordMatch(String keyword);

	/**
	 * 정확한 검색어에 해당하는 AutoComplete 레코드의 search_count를 1 증가시킵니다.
	 */
	@Modifying(clearAutomatically = true)
	@Query("UPDATE AutoComplete a SET a.searchCount = a.searchCount + 1 WHERE a.name = :keyword")
	void incrementSearchCount(@Param("keyword") String keyword);
}
