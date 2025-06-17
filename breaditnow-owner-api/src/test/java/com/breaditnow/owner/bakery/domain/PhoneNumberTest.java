package com.breaditnow.owner.bakery.domain;

import com.breaditnow.owner.global.exception.OwnerErrorCode;
import com.breaditnow.owner.global.exception.OwnerException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("PhoneNumber 도메인 테스트")
class PhoneNumberTest {

    @Nested
    @DisplayName("생성")
    class Creation {

        @Test
        @DisplayName("정상적인 전화번호로 생성된다")
        void createSuccessfully() {
            // given
            String value = "010-1234-5678";

            // when
            PhoneNumber phoneNumber = PhoneNumber.create(value);

            // then
            assertThat(phoneNumber.value()).isEqualTo(value);
        }

        @ParameterizedTest
        @ValueSource(strings = {"", " ", "\t", "\n", "\r", "   "})
        @DisplayName("빈 값 또는 공백 입력 시 예외 발생")
        void throwExceptionWhenValueIsEmpty(String input) {
            assertThatThrownBy(() -> PhoneNumber.create(input))
                    .isInstanceOf(OwnerException.class)
                    .hasFieldOrPropertyWithValue("errorCode", OwnerErrorCode.PHONE_NUMBER_REQUIRED);
        }

        @ParameterizedTest
        @ValueSource(strings = {
                "01012345678",      // 하이픈 없음
                "abc-defg-hijk",    // 문자 포함
                "010-123-45678",    // 중간 자리수 부족
                "010-12-5678",      // 중간 자리수 부족
                "010-12345-6789",   // 중간 자리수 초과
                "02-1234-567",      // 마지막 자리수 부족
                "010-1234-56789",   // 마지막 자리수 초과
                "010--1234-5678",   // 하이픈 중복
                "010-1234--5678"    // 하이픈 중복
        })
        @DisplayName("잘못된 형식의 전화번호 입력 시 예외 발생")
        void throwExceptionWhenInvalidFormat(String input) {
            assertThatThrownBy(() -> PhoneNumber.create(input))
                    .isInstanceOf(OwnerException.class)
                    .hasFieldOrPropertyWithValue("errorCode", OwnerErrorCode.INVALID_PHONE_NUMBER_FORMAT);
        }
    }
}