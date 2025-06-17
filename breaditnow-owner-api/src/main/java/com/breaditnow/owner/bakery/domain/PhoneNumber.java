package com.breaditnow.owner.bakery.domain;

import com.breaditnow.owner.global.exception.OwnerErrorCode;
import com.breaditnow.owner.global.exception.OwnerException;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.Embeddable;

@Embeddable
public record PhoneNumber(String value) {
    public PhoneNumber {
        if (StringUtils.isBlank(value)) {
            throw new OwnerException(OwnerErrorCode.PHONE_NUMBER_REQUIRED);
        }

        String exactFormatRegex = "^\\d{2,3}-\\d{3,4}-\\d{4}$";
        if (!value.matches(exactFormatRegex)) {
            throw new OwnerException(OwnerErrorCode.INVALID_PHONE_NUMBER_FORMAT);
        }
    }

    public static PhoneNumber create(String value) {
        return new PhoneNumber(value);
    }
}
