package com.breaditnow.common.domain;

public record UserIdentifier(
        Long id,
        Role type
) {
}
