package com.breaditnow.customer.alert.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.util.EnumSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class DayOfWeekSetTest {

    @Nested
    @DisplayName("DayOfWeekSet 생성 테스트")
    class CreateTest {
        @Test
        @DisplayName("유효한 요일 집합으로 DayOfWeekSet을 생성할 수 있다")
        void createWithValidDays() {
            // given
            Set<DayOfWeek> days = EnumSet.of(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY);

            // when & then
            assertDoesNotThrow(() -> new DayOfWeekSet(days));
        }

        @Test
        @DisplayName("정적 팩토리 메서드로 DayOfWeekSet을 생성할 수 있다")
        void createWithFactoryMethod() {
            // given
            Set<DayOfWeek> days = EnumSet.of(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY);

            // when
            DayOfWeekSet dayOfWeekSet = DayOfWeekSet.of(days);

            // then
            assertThat(dayOfWeekSet.days()).containsExactlyInAnyOrderElementsOf(days);
        }

        @Test
        @DisplayName("빈 요일 집합으로도 DayOfWeekSet을 생성할 수 있다")
        void createWithEmptyDays() {
            // given
            Set<DayOfWeek> emptyDays = EnumSet.noneOf(DayOfWeek.class);

            // when & then
            assertDoesNotThrow(() -> new DayOfWeekSet(emptyDays));
        }
    }

    @Nested
    @DisplayName("DayOfWeekSet 조회 테스트")
    class QueryTest {
        @Test
        @DisplayName("특정 요일이 포함되어 있는지 확인할 수 있다")
        void containsTest() {
            // given
            DayOfWeekSet dayOfWeekSet = DayOfWeekSet.of(EnumSet.of(DayOfWeek.MONDAY, DayOfWeek.FRIDAY));

            // when & then
            assertThat(dayOfWeekSet.contains(DayOfWeek.MONDAY)).isTrue();
            assertThat(dayOfWeekSet.contains(DayOfWeek.FRIDAY)).isTrue();
            assertThat(dayOfWeekSet.contains(DayOfWeek.TUESDAY)).isFalse();
        }

        @Test
        @DisplayName("요일 집합을 수정할 수 없다")
        void daysAreImmutable() {
            // given
            DayOfWeekSet dayOfWeekSet = DayOfWeekSet.of(EnumSet.of(DayOfWeek.MONDAY));
            Set<DayOfWeek> days = dayOfWeekSet.days();

            // when & then
            assertThatThrownBy(() -> days.add(DayOfWeek.TUESDAY))
                    .isInstanceOf(UnsupportedOperationException.class);
        }

    }

    @Nested
    @DisplayName("빈 DayOfWeekSet 테스트")
    class EmptySetTest {
        @Test
        @DisplayName("empty() 메서드로 빈 DayOfWeekSet을 생성할 수 있다")
        void createEmptySet() {
            // when
            DayOfWeekSet emptySet = DayOfWeekSet.empty();

            // then
            assertThat(emptySet.days()).isEmpty();
        }

        @Test
        @DisplayName("빈 DayOfWeekSet은 어떤 요일도 포함하지 않는다")
        void emptySetContainsNoDays() {
            // given
            DayOfWeekSet emptySet = DayOfWeekSet.empty();

            // when & then
            for (DayOfWeek day : DayOfWeek.values()) {
                assertThat(emptySet.contains(day)).isFalse();
            }
        }
    }
}