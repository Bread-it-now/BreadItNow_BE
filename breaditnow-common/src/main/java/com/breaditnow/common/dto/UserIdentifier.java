package com.breaditnow.common.dto;

import com.breaditnow.common.domain.Role;

public record UserIdentifier(
        Long id,
        Role type
) {
}
