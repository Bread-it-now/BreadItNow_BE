package com.breaditnow.customer.alert.domain;

import com.breaditnow.alert.domain.GlobalAlertSetting;
import com.breaditnow.common.exception.CustomerException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

import static com.breaditnow.common.exception.CustomerErrorCode.INVALID_TIME_RANGE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("DoNotDisturb 도메인 테스트")
class GlobalAlertSettingTest {

    @Nested
    @DisplayName("DoNotDisturb 생성")
    class Creation {

        @Test
        @DisplayName("정상적인 파라미터로 DoNotDisturb를 생성한다")
        void createSuccess() {
            // given
            Set<String> days = Set.of("MONDAY", "WEDNESDAY");
            LocalTime startTime = LocalTime.of(10, 0);
            LocalTime endTime = LocalTime.of(18, 0);

            // when
            GlobalAlertSetting dnd = GlobalAlertSetting.create(days, startTime, endTime, true);

            // then
            assertThat(dnd.isActive()).isTrue();
            assertThat(dnd.getDays().days()).containsExactlyInAnyOrder(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY);
            assertThat(dnd.getStartTime().toLocalTime()).isEqualTo(startTime);
            assertThat(dnd.getEndTime().toLocalTime()).isEqualTo(endTime);
        }

        @Test
        @DisplayName("시작 시간이 종료 시간보다 늦으면 예외가 발생한다")
        void throwExceptionWhenStartTimeAfterEndTime() {
            // given
            Set<String> days = Set.of("MONDAY");
            LocalTime startTime = LocalTime.of(18, 0);
            LocalTime endTime = LocalTime.of(10, 0);

            // when & then
            assertThatThrownBy(() -> GlobalAlertSetting.create(days, startTime, endTime, true))
                    .isInstanceOf(CustomerException.class)
                    .hasFieldOrPropertyWithValue("errorCode", INVALID_TIME_RANGE);
        }
    }

    @Nested
    @DisplayName("시간 범위 확인")
    class TimeRangeCheck {

        @ParameterizedTest
        @CsvSource({
                "MONDAY,12:00,true",     // 범위 내
                "TUESDAY,12:00,false",   // 다른 요일
                "MONDAY,09:59,false",    // 시작 시간 이전
                "MONDAY,18:01,false",    // 종료 시간 이후
        })
        @DisplayName("주어진 시간이 방해금지 범위 내에 있는지 확인한다")
        void isWithinTimeRange(DayOfWeek dayOfWeek, String time, boolean expected) {
            // given
            GlobalAlertSetting dnd = createActiveDoNotDisturb();
            String[] timeParts = time.split(":");
            LocalDateTime dateTime = LocalDateTime.now()
                    .withHour(Integer.parseInt(timeParts[0]))
                    .withMinute(Integer.parseInt(timeParts[1]))
                    .with(dayOfWeek);

            // when
            boolean result = dnd.isWithin(dateTime);

            // then
            assertThat(result).isEqualTo(expected);
        }

        @Test
        @DisplayName("비활성화 상태에서는 항상 범위 밖으로 판단한다")
        void alwaysOutOfRangeWhenInactive() {
            // given
            GlobalAlertSetting dnd = createInactiveDoNotDisturb();
            LocalDateTime dateTime = LocalDateTime.now()
                    .withHour(12)
                    .withMinute(0)
                    .with(DayOfWeek.MONDAY);

            // when
            boolean result = dnd.isWithin(dateTime);

            // then
            assertThat(result).isFalse();
        }
    }

    private GlobalAlertSetting createActiveDoNotDisturb() {
        return GlobalAlertSetting.create(
                Set.of("MONDAY"),
                LocalTime.of(10, 0),
                LocalTime.of(18, 0),
                true
        );
    }

    private GlobalAlertSetting createInactiveDoNotDisturb() {
        return GlobalAlertSetting.create(
                Set.of("MONDAY"),
                LocalTime.of(10, 0),
                LocalTime.of(18, 0),
                false
        );
    }
}