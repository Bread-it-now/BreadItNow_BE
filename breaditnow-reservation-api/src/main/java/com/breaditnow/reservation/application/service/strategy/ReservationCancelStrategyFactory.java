package com.breaditnow.reservation.application.service.strategy;

import com.breaditnow.common.domain.Role;
import com.breaditnow.common.exception.ReservationException;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.breaditnow.common.exception.ReservationErrorCode.FORBIDDEN_ACCESS;

@Component
public class ReservationCancelStrategyFactory {

    private final Map<Role, ReservationCancelStrategy> strategies;

    public ReservationCancelStrategyFactory(Set<ReservationCancelStrategy> strategySet) {
        strategies = strategySet.stream()
                .collect(Collectors.toMap(ReservationCancelStrategy::getRole, s -> s));
    }

    public ReservationCancelStrategy findStrategy(Role role) {
        return Optional.ofNullable(strategies.get(role))
                .orElseThrow(() -> new ReservationException(FORBIDDEN_ACCESS));
    }
}
