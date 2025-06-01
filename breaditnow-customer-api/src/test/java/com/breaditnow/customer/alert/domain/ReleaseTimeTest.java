package com.breaditnow.customer.alert.domain;

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

class ReleaseTimeTest {

    @Nested
    @DisplayName("ReleaseTime 생성 테스트")
    class CreateTest {
        @Test
        @DisplayName("문자열로부터 ReleaseTime을 생성할 수 있다")
        void createFromString() {
            // when
            ReleaseTime releaseTime = ReleaseTime.of("13:30");

            // then
            assertThat(releaseTime.toLocalTime()).isEqualTo(LocalTime.of(13, 30));
        }

        @ParameterizedTest
        @ValueSource(strings = {"25:00", "13:60", "abc", "13-30", "13:3"})
        @DisplayName("잘못된 형식의 시간 문자열로 생성 시 예외가 발생한다")
        void createFailWithInvalidFormat(String invalidTime) {
            assertThatThrownBy(() -> ReleaseTime.of(invalidTime))
                    .isInstanceOf(CustomerException.class)
                    .hasFieldOrPropertyWithValue("errorCode", INVALID_TIME_FORMAT);
        }

        @Test
        @DisplayName("LocalTime으로부터 ReleaseTime을 생성할 수 있다")
        void createFromLocalTime() {
            // given
            LocalTime localTime = LocalTime.of(13, 30);

            // when
            ReleaseTime releaseTime = ReleaseTime.fromLocalTime(localTime);

            // then
            assertThat(releaseTime.toLocalTime()).isEqualTo(localTime);
        }

        @Test
        @DisplayName("null LocalTime으로 생성 시 예외가 발생한다")
        void createFailWithNullLocalTime() {
            assertThatThrownBy(() -> ReleaseTime.fromLocalTime(null))
                    .isInstanceOf(CustomerException.class)
                    .hasFieldOrPropertyWithValue("errorCode", INVALID_TIME_FORMAT);
        }
    }

    @Nested
    @DisplayName("ReleaseTime 비교 테스트")
    class CompareTest {
        @Test
        @DisplayName("같은 시간은 동등하다")
        void equalTimes() {
            // given
            ReleaseTime time1 = ReleaseTime.of("13:30");
            ReleaseTime time2 = ReleaseTime.of("13:30");

            // when & then
            assertThat(time1).isEqualTo(time2);
            assertThat(time1.compareTo(time2)).isZero();
        }

        @Test
        @DisplayName("이후 시간과 비교하면 음수를 반환한다")
        void compareWithLaterTime() {
            // given
            ReleaseTime earlier = ReleaseTime.of("13:30");
            ReleaseTime later = ReleaseTime.of("14:30");

            // when & then
            assertThat(earlier.compareTo(later)).isNegative();
        }

        @Test
        @DisplayName("이전 시간과 비교하면 양수를 반환한다")
        void compareWithEarlierTime() {
            // given
            ReleaseTime later = ReleaseTime.of("14:30");
            ReleaseTime earlier = ReleaseTime.of("13:30");

            // when & then
            assertThat(later.compareTo(earlier)).isPositive();
        }
    }

    @Test
    @DisplayName("LocalTime 변환 테스트")
    void conversionTest() {
        // given
        LocalTime originalTime = LocalTime.of(13, 30);
        ReleaseTime releaseTime = ReleaseTime.fromLocalTime(originalTime);

        // when
        LocalTime convertedTime = releaseTime.toLocalTime();

        // then
        assertThat(convertedTime).isEqualTo(originalTime);
    }

    @Test
    @DisplayName("동등성 비교 테스트")
    void equalityTest() {
        // given
        ReleaseTime time1 = ReleaseTime.of("13:30");
        ReleaseTime time2 = ReleaseTime.of("13:30");
        ReleaseTime time3 = ReleaseTime.of("14:30");

        // when & then
        assertThat(time1)
                .isEqualTo(time2)
                .isNotEqualTo(time3);
        assertThat(time1.hashCode()).isEqualTo(time2.hashCode());
    }
}