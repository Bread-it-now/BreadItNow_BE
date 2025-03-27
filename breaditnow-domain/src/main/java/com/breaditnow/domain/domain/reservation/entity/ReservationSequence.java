package com.breaditnow.domain.domain.reservation.entity;

import com.breaditnow.domain.global.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationSequence extends BaseEntity {

    @Id
    private LocalDate date;

    private int currentNumber;

    public int nextNumber() {
        this.currentNumber++;
        return this.currentNumber;
    }

    @Builder
    public ReservationSequence(LocalDate date, int currentNumber) {
        this.date = date;
        this.currentNumber = currentNumber;
    }
}
