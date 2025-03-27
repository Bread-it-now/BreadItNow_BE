package com.breaditnow.customer.domain.reservation;


import com.breaditnow.domain.domain.reservation.entity.ReservationSequence;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ReservationSequenceTest {

    @Test
    @DisplayName("같은 날짜면 예약 번호가 순차적으로 증가한다")
    void sameDayIncreaseNumber() {
        Map<LocalDate, ReservationSequence> sequenceMap = new HashMap<>();
        LocalDate today = LocalDate.of(2025, 3, 26);

        ReservationSequence seq1 = sequenceMap.computeIfAbsent(today, d -> new ReservationSequence(d, 1));
        seq1.nextNumber();
        ReservationSequence seq2 = sequenceMap.get(today);

        assertThat(seq1.getCurrentNumber()).isEqualTo(2);
        assertThat(seq2.getCurrentNumber()).isEqualTo(2);
    }

    @Test
    @DisplayName("날짜가 바뀌면 예약 번호는 1부터 다시 시작한다")
    void newDayResetNumber() {
        Map<LocalDate, ReservationSequence> map = new HashMap<>();
        LocalDate day1 = LocalDate.of(2025, 3, 26);
        LocalDate day2 = LocalDate.of(2025, 3, 27);

        ReservationSequence seq1 = map.computeIfAbsent(day1, d -> new ReservationSequence(d, 1));
        seq1.nextNumber();

        ReservationSequence seq2 = map.computeIfAbsent(day2, d -> new ReservationSequence(d, 1));

        assertThat(seq1.getCurrentNumber()).isEqualTo(2);
        assertThat(seq2.getCurrentNumber()).isEqualTo(1);
    }
}
