package com.breaditnow.customer.alert.domain;

import com.breaditnow.customer.common.exception.CustomerException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.util.Set;

import static com.breaditnow.customer.common.exception.CustomerErrorCode.EMPTY_DND_DAYS;
import static com.breaditnow.customer.common.exception.CustomerErrorCode.INVALID_DND_DAY_VALUE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("DayOfWeekSet 도메인 테스트")
class DayOfWeekSetTest {

    @Nested
    @DisplayName("DayOfWeekSet 생성")
    class Creation {

        @Test
        @DisplayName("문자열 요일 집합으로 DayOfWeekSet을 생성한다")
        void createFromStringSet() {
            // given
            Set<String> dayStrings = Set.of("MONDAY", "WEDNESDAY", "FRIDAY");

            // when
            DayOfWeekSet dayOfWeekSet = DayOfWeekSet.of(dayStrings);

            // then
            assertThat(dayOfWeekSet.days())
                    .containsExactlyInAnyOrder(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY);
        }

        @Test
        @DisplayName("빈 요일 집합으로 생성 시 예외가 발생한다")
        void throwExceptionWhenEmptySet() {
            // given
            Set<String> emptyDays = Set.of();

            // when & then
            assertThatThrownBy(() -> DayOfWeekSet.of(emptyDays))
                    .isInstanceOf(CustomerException.class)
                    .hasFieldOrPropertyWithValue("errorCode", EMPTY_DND_DAYS);
        }

        @Test
        @DisplayName("null 요일 집합으로 생성 시 예외가 발생한다")
        void throwExceptionWhenNullSet() {
            assertThatThrownBy(() -> DayOfWeekSet.of(null))
                    .isInstanceOf(CustomerException.class)
                    .hasFieldOrPropertyWithValue("errorCode", EMPTY_DND_DAYS);
        }

        @Test
        @DisplayName("잘못된 요일 문자열로 생성 시 예외가 발생한다")
        void throwExceptionWhenInvalidDayString() {
            // given
            Set<String> invalidDays = Set.of("MONDAY", "INVALID_DAY");

            // when & then
            assertThatThrownBy(() -> DayOfWeekSet.of(invalidDays))
                    .isInstanceOf(CustomerException.class)
                    .hasFieldOrPropertyWithValue("errorCode", INVALID_DND_DAY_VALUE);
        }

        @Test
        @DisplayName("empty() 메서드로 빈 DayOfWeekSet을 생성할 수 있다")
        void createEmptySet() {
            // when
            DayOfWeekSet emptySet = DayOfWeekSet.empty();

            // then
            assertThat(emptySet.days()).isEmpty();
        }
    }

    @Nested
    @DisplayName("DayOfWeekSet 조회")
    class Query {

        @Test
        @DisplayName("특정 요일이 포함되어 있는지 확인한다")
        void containsDay() {
            // given
            DayOfWeekSet dayOfWeekSet = DayOfWeekSet.of(Set.of("MONDAY", "WEDNESDAY"));

            // then
            assertThat(dayOfWeekSet.contains(DayOfWeek.MONDAY)).isTrue();
            assertThat(dayOfWeekSet.contains(DayOfWeek.WEDNESDAY)).isTrue();
            assertThat(dayOfWeekSet.contains(DayOfWeek.FRIDAY)).isFalse();
        }

        @Test
        @DisplayName("days() 메서드는 수정 불가능한 Set을 반환한다")
        void daysReturnsUnmodifiableSet() {
            // given
            DayOfWeekSet dayOfWeekSet = DayOfWeekSet.of(Set.of("MONDAY"));
            Set<DayOfWeek> days = dayOfWeekSet.days();

            // when & then
            assertThatThrownBy(() -> days.add(DayOfWeek.TUESDAY))
                    .isInstanceOf(UnsupportedOperationException.class);
        }
    }

    @Nested
    @DisplayName("DayOfWeekSet 동등성 비교")
    class Equality {

        @Test
        @DisplayName("같은 요일들을 가진 DayOfWeekSet은 동등하다")
        void equalWhenSameDays() {
            // given
            DayOfWeekSet set1 = DayOfWeekSet.of(Set.of("MONDAY", "WEDNESDAY"));
            DayOfWeekSet set2 = DayOfWeekSet.of(Set.of("MONDAY", "WEDNESDAY"));

            // then
            assertThat(set1)
                    .isEqualTo(set2)
                    .hasSameHashCodeAs(set2);
        }

        @Test
        @DisplayName("다른 요일들을 가진 DayOfWeekSet은 동등하지 않다")
        void notEqualWhenDifferentDays() {
            // given
            DayOfWeekSet set1 = DayOfWeekSet.of(Set.of("MONDAY", "WEDNESDAY"));
            DayOfWeekSet set2 = DayOfWeekSet.of(Set.of("MONDAY", "FRIDAY"));

            // then
            assertThat(set1).isNotEqualTo(set2);
        }
    }
}