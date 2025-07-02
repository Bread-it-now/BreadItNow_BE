package com.breaditnow.common.domain;

import com.breaditnow.common.exception.BreaditnowException;
import com.breaditnow.common.util.ValidationUtils;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import static com.breaditnow.common.exception.CommonErrorCode.INVALID_AMOUNT;

@Getter
@EqualsAndHashCode
public class Money {
    public static final Money ZERO = new Money(0);

    private final Integer amount;

    public Money(Integer amount) {
        ValidationUtils.requireValid(amount, o -> o < 0, () -> new BreaditnowException(INVALID_AMOUNT));
        this.amount = amount;
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


