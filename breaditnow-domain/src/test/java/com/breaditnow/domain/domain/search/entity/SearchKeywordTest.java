package com.breaditnow.domain.domain.search.entity;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SearchKeywordTest {

	@Test
	@DisplayName("단어 사이에 공백이 있을 경우 각 단어 앞에 '+'가 추가되어야 함 (입력: '띄어 쓰기')")
	public void testToBooleanModeQuery_SimpleCase() {
		SearchKeyword searchKeyword = new SearchKeyword("띄어 쓰기");

		String expected = "+띄어 +쓰기";

		String result = searchKeyword.toBooleanModeQuery();
		assertThat(result).isEqualTo(expected);
	}

	@Test
	@DisplayName("앞뒤 및 중복 공백이 있는 경우에도 올바르게 변환되어야 함 (입력: '   띄어   쓰기   ')")
	public void testToBooleanModeQuery_MultipleSpaces() {
		SearchKeyword searchKeyword = new SearchKeyword("   띄어   쓰기   ");
		String expected = "+띄어 +쓰기";
		String result = searchKeyword.toBooleanModeQuery();
		assertThat(result).isEqualTo(expected);
	}

	@Test
	@DisplayName("단일 단어 입력 시에도 '+'가 붙어야 함 (입력: '띄어')")
	public void testToBooleanModeQuery_SingleWord() {
		SearchKeyword searchKeyword = new SearchKeyword("띄어");

		String expected = "+띄어";

		String result = searchKeyword.toBooleanModeQuery();
		assertThat(result).isEqualTo(expected);
	}

	@Test
	@DisplayName("빈 문자열 입력 시 결과는 빈 문자열이어야 함")
	public void testToBooleanModeQuery_EmptyString() {
		SearchKeyword searchKeyword = new SearchKeyword("");
		String expected = "";
		String result = searchKeyword.toBooleanModeQuery();
		assertThat(result).isEqualTo(expected);
	}

	@Test
	@DisplayName("공백이 없는 단어 입력 시 전체 문자열에 '+'가 붙어야 함 (입력: '띄어쓰기')")
	public void testToBooleanModeQuery_NoWhitespace() {
		// 입력: "띄어쓰기" -> 기대: "+띄어쓰기"
		SearchKeyword searchKeyword = new SearchKeyword("띄어쓰기");

		String expected = "+띄어쓰기";

		String result = searchKeyword.toBooleanModeQuery();
		assertThat(result).isEqualTo(expected);
	}
}
