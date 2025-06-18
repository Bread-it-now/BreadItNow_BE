package com.breaditnow.owner.common.domain;

import com.breaditnow.owner.global.exception.OwnerException;
import jakarta.persistence.Embeddable;

import static com.breaditnow.owner.common.domain.ValidationUtils.requireValid;
import static com.breaditnow.owner.global.exception.OwnerErrorCode.INVALID_AMOUNT;


@Embeddable
public record Money(
        Integer amount
) {
    public static final Money ZERO = new Money(0);

    public Money {
        requireValid(amount, o -> o < 0, () -> new OwnerException(INVALID_AMOUNT));
    }

    public Money add(Money other) {
        return new Money(this.amount + other.amount);
    }

    public Money subtract(Money other) {
        return new Money(this.amount - other.amount);
    }

    public Money multiply(Integer multiplier) {
        return new Money(this.amount * multiplier);
    }
}
