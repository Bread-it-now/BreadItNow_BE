package com.breaditnow.customer.common.infrastructure.entity;

import com.breaditnow.customer.common.domain.Money;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class MoneyConverter implements AttributeConverter<Money, Integer> {
    @Override
    public Integer convertToDatabaseColumn(Money attribute) {
        return attribute == null ? null : attribute.getAmount();
    }

    @Override
    public Money convertToEntityAttribute(Integer dbData) {
        return dbData == null ? null : new Money(dbData);
    }
}
