package com.breaditnow.domain.domain.search.repository;

import java.util.Collections;
import java.util.List;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.breaditnow.domain.domain.search.enumerate.SearchType;
import com.breaditnow.domain.global.dto.AutoCompleteDto;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AutocompleteJdbcDaoImpl implements AutocompleteJdbcDao {
	private static final String SQL = """
		SELECT name, type
		  FROM (
		        SELECT b.name   AS name
		             , 'BAKERY' AS type
		             , MATCH(b.name) AGAINST(:kw IN NATURAL LANGUAGE MODE) AS score
		          FROM bakery b
		         WHERE MATCH(b.name) AGAINST(:kw IN NATURAL LANGUAGE MODE)
		        UNION ALL
		        SELECT p.name    AS name
		             , 'PRODUCT' AS type
		             , MATCH(p.name) AGAINST(:kw IN NATURAL LANGUAGE MODE) AS score
		          FROM product p
		         WHERE MATCH(p.name) AGAINST(:kw IN NATURAL LANGUAGE MODE)
		       ) AS combined
		 ORDER BY combined.score DESC
		 LIMIT 10
		""";

	private final NamedParameterJdbcTemplate jdbc;

	@Override
	public List<AutoCompleteDto> findByKeywordMatch(String keyword) {
		var params = Collections.singletonMap("kw", keyword);
		return jdbc.query(SQL, params, (rs, rowNum) ->
			new AutoCompleteDto(
				rs.getString("name"),
				SearchType.valueOf(rs.getString("type"))
			)
		);
	}
}
