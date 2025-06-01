package com.breaditnow.customer.alert.domain;

import com.breaditnow.customer.common.exception.CustomerException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.EnumSet;

import static com.breaditnow.customer.common.exception.CustomerErrorCode.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DoNotDisturbTest {

    @Nested
    @DisplayName("방해금지 모드 생성 테스트")
    class CreateTest {
        @Test
        @DisplayName("정상적인 방해금지 모드를 생성할 수 있다")
        void createSuccess() {
            // given
            DayOfWeekSet days = DayOfWeekSet.of(EnumSet.of(DayOfWeek.MONDAY));
            ReleaseTime startTime = ReleaseTime.of("10:00");
            ReleaseTime endTime = ReleaseTime.of("18:00");

            // when
            DoNotDisturb dnd = DoNotDisturb.builder()
                    .days(days)
                    .startTime(startTime)
                    .endTime(endTime)
                    .active(true)
                    .build();

            // then
            assertThat(dnd.isActive()).isTrue();
            assertThat(dnd.getDays()).isEqualTo(days);
            assertThat(dnd.getStartTime()).isEqualTo(startTime);
            assertThat(dnd.getEndTime()).isEqualTo(endTime);
        }

        @Test
        @DisplayName("시작 시간이 null이면 예외가 발생한다")
        void createFailWithNullStartTime() {
            // given
            DayOfWeekSet days = DayOfWeekSet.of(EnumSet.of(DayOfWeek.MONDAY));
            ReleaseTime endTime = ReleaseTime.of("18:00");

            // when & then
            assertThatThrownBy(() -> DoNotDisturb.builder()
                    .days(days)
                    .startTime(null)
                    .endTime(endTime)
                    .active(true)
                    .build())
                    .isInstanceOf(CustomerException.class)
                    .hasFieldOrPropertyWithValue("errorCode", INVALID_START_TIME);
        }

        @Test
        @DisplayName("종료 시간이 null이면 예외가 발생한다")
        void createFailWithNullEndTime() {
            // given
            DayOfWeekSet days = DayOfWeekSet.of(EnumSet.of(DayOfWeek.MONDAY));
            ReleaseTime startTime = ReleaseTime.of("10:00");

            // when & then
            assertThatThrownBy(() -> DoNotDisturb.builder()
                    .days(days)
                    .startTime(startTime)
                    .endTime(null)
                    .active(true)
                    .build())
                    .isInstanceOf(CustomerException.class)
                    .hasFieldOrPropertyWithValue("errorCode", INVALID_END_TIME);
        }

        @Test
        @DisplayName("시작 시간이 종료 시간보다 이후면 예외가 발생한다")
        void createFailWithInvalidTimeRange() {
            // given
            DayOfWeekSet days = DayOfWeekSet.of(EnumSet.of(DayOfWeek.MONDAY));
            ReleaseTime startTime = ReleaseTime.of("18:00");
            ReleaseTime endTime = ReleaseTime.of("10:00");

            // when & then
            assertThatThrownBy(() -> DoNotDisturb.builder()
                    .days(days)
                    .startTime(startTime)
                    .endTime(endTime)
                    .active(true)
                    .build())
                    .isInstanceOf(CustomerException.class)
                    .hasFieldOrPropertyWithValue("errorCode", INVALID_TIME_RANGE);
        }

        @Test
        @DisplayName("빈 요일 집합이면 예외가 발생한다")
        void createFailWithEmptyDays() {
            // given
            DayOfWeekSet days = DayOfWeekSet.empty();
            ReleaseTime startTime = ReleaseTime.of("10:00");
            ReleaseTime endTime = ReleaseTime.of("18:00");

            // when & then
            assertThatThrownBy(() -> DoNotDisturb.builder()
                    .days(days)
                    .startTime(startTime)
                    .endTime(endTime)
                    .active(true)
                    .build())
                    .isInstanceOf(CustomerException.class)
                    .hasFieldOrPropertyWithValue("errorCode", INVALID_DND_DAYS);
        }
    }

    @Nested
    @DisplayName("방해금지 모드 활성화/비활성화 테스트")
    class ToggleTest {
        @Test
        @DisplayName("비활성화된 방해금지 모드를 활성화할 수 있다")
        void activateSuccess() {
            // given
            DoNotDisturb dnd = createInactiveDoNotDisturb();

            // when
            dnd.activate();

            // then
            assertThat(dnd.isActive()).isTrue();
        }

        @Test
        @DisplayName("이미 활성화된 상태에서 활성화하면 예외가 발생한다")
        void activateFailWhenAlreadyActive() {
            // given
            DoNotDisturb dnd = createActiveDoNotDisturb();

            // when & then
            assertThatThrownBy(dnd::activate)
                    .isInstanceOf(CustomerException.class)
                    .hasFieldOrPropertyWithValue("errorCode", ALREADY_ACTIVE);
        }

        @Test
        @DisplayName("활성화된 방해금지 모드를 비활성화할 수 있다")
        void deactivateSuccess() {
            // given
            DoNotDisturb dnd = createActiveDoNotDisturb();

            // when
            dnd.deactivate();

            // then
            assertThat(dnd.isActive()).isFalse();
        }

        @Test
        @DisplayName("이미 비활성화된 상태에서 비활성화하면 예외가 발생한다")
        void deactivateFailWhenAlreadyInactive() {
            // given
            DoNotDisturb dnd = createInactiveDoNotDisturb();

            // when & then
            assertThatThrownBy(dnd::deactivate)
                    .isInstanceOf(CustomerException.class)
                    .hasFieldOrPropertyWithValue("errorCode", ALREADY_INACTIVE);
        }
    }

    @Nested
    @DisplayName("방해금지 시간 범위 체크 테스트")
    class TimeRangeTest {
        @ParameterizedTest
        @CsvSource({
            "MONDAY,12:00,true",      // 범위 내 (활성화 상태)
            "TUESDAY,12:00,false",    // 다른 요일
            "MONDAY,09:59,false",     // 시작 시간 이전
            "MONDAY,18:01,false",     // 종료 시간 이후
        })
        @DisplayName("현재 시간이 방해금지 범위 내인지 확인한다")
        void isWithinTest(DayOfWeek dayOfWeek, String time, boolean expected) {
            // given
            DoNotDisturb dnd = createActiveDoNotDisturb();
            LocalDateTime now = LocalDateTime.of(2024, 1, 1,
                    Integer.parseInt(time.split(":")[0]),
                    Integer.parseInt(time.split(":")[1]));
            now = now.with(dayOfWeek);

            // when
            boolean result = dnd.isWithin(now);

            // then
            assertThat(result).isEqualTo(expected);
        }

        @Test
        @DisplayName("비활성화 상태에서는 항상 false를 반환한다")
        void isWithinWhenInactive() {
            // given
            DoNotDisturb dnd = createInactiveDoNotDisturb();
            LocalDateTime now = LocalDateTime.of(2024, 1, 1, 12, 0)
                    .with(DayOfWeek.MONDAY);

            // when
            boolean result = dnd.isWithin(now);

            // then
            assertThat(result).isFalse();
        }
    }

    private DoNotDisturb createActiveDoNotDisturb() {
        return DoNotDisturb.builder()
                .days(DayOfWeekSet.of(EnumSet.of(DayOfWeek.MONDAY)))
                .startTime(ReleaseTime.of("10:00"))
                .endTime(ReleaseTime.of("18:00"))
                .active(true)
                .build();
    }

    private DoNotDisturb createInactiveDoNotDisturb() {
        return DoNotDisturb.builder()
                .days(DayOfWeekSet.of(EnumSet.of(DayOfWeek.MONDAY)))
                .startTime(ReleaseTime.of("10:00"))
                .endTime(ReleaseTime.of("18:00"))
                .active(false)
                .build();
    }
}