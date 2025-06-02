package com.breaditnow.customer.alert.domain;

import com.breaditnow.customer.common.domain.ReleaseTime;
import com.breaditnow.customer.common.exception.CustomerException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalTime;

import static com.breaditnow.customer.common.exception.CustomerErrorCode.INVALID_TIME_FORMAT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("ReleaseTime 도메인 테스트")
class ReleaseTimeTest {

    @Nested
    @DisplayName("ReleaseTime 생성")
    class Creation {

        @Test
        @DisplayName("문자열로 ReleaseTime을 생성한다")
        void createFromString() {
            // given
            String timeStr = "13:30";
            LocalTime expectedTime = LocalTime.of(13, 30);

            // when
            ReleaseTime releaseTime = ReleaseTime.of(timeStr);

            // then
            assertThat(releaseTime.toLocalTime()).isEqualTo(expectedTime);
        }

        @Test
        @DisplayName("LocalTime으로 ReleaseTime을 생성한다")
        void createFromLocalTime() {
            // given
            LocalTime time = LocalTime.of(13, 30);

            // when
            ReleaseTime releaseTime = ReleaseTime.of(time);

            // then
            assertThat(releaseTime.toLocalTime()).isEqualTo(time);
        }

        @Test
        @DisplayName("null LocalTime으로 생성 시 예외가 발생한다")
        void throwExceptionWhenLocalTimeIsNull() {
            assertThatThrownBy(() -> ReleaseTime.of((LocalTime) null))
                    .isInstanceOf(CustomerException.class)
                    .hasFieldOrPropertyWithValue("errorCode", INVALID_TIME_FORMAT);
        }

        @ParameterizedTest
        @ValueSource(strings = {"25:00", "13:60", "abc", "13-30", "13:3", ""})
        @DisplayName("잘못된 형식의 시간 문자열로 생성 시 예외가 발생한다")
        void throwExceptionWhenInvalidTimeFormat(String invalidTime) {
            assertThatThrownBy(() -> ReleaseTime.of(invalidTime))
                    .isInstanceOf(CustomerException.class)
                    .hasFieldOrPropertyWithValue("errorCode", INVALID_TIME_FORMAT);
        }
    }

    @Nested
    @DisplayName("ReleaseTime 비교")
    class Comparison {

        @Test
        @DisplayName("동일한 시간은 같다고 판단한다")
        void equals() {
            // given
            ReleaseTime time1 = ReleaseTime.of("13:30");
            ReleaseTime time2 = ReleaseTime.of("13:30");

            // then
            assertThat(time1)
                    .isEqualTo(time2)
                    .hasSameHashCodeAs(time2);
            assertThat(time1.compareTo(time2)).isZero();
        }

        @Test
        @DisplayName("서로 다른 시간은 다르다고 판단한다")
        void notEquals() {
            // given
            ReleaseTime time1 = ReleaseTime.of("13:30");
            ReleaseTime time2 = ReleaseTime.of("14:30");

            // then
            assertThat(time1).isNotEqualTo(time2);
        }

        @Test
        @DisplayName("이전 시간과 비교하면 양수를 반환한다")
        void compareToEarlierTime() {
            // given
            ReleaseTime later = ReleaseTime.of("14:30");
            ReleaseTime earlier = ReleaseTime.of("13:30");

            // then
            assertThat(later.compareTo(earlier)).isPositive();
        }

        @Test
        @DisplayName("이후 시간과 비교하면 음수를 반환한다")
        void compareToLaterTime() {
            // given
            ReleaseTime earlier = ReleaseTime.of("13:30");
            ReleaseTime later = ReleaseTime.of("14:30");

            // then
            assertThat(earlier.compareTo(later)).isNegative();
        }
    }
}