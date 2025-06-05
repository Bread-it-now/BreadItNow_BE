package com.breaditnow.customer.product.domain;

import com.breaditnow.customer.common.domain.ReleaseTime;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ReleaseTimes {
    private List<ReleaseTime> releaseTimes = new ArrayList<>();

    public ReleaseTimes(List<ReleaseTime> releaseTimes) {
        this.releaseTimes = releaseTimes;
    }

    public static ReleaseTimes of(List<String> releaseTimes) {
        if (releaseTimes.isEmpty()) return new ReleaseTimes(List.of());
        List<ReleaseTime> releaseTimeList = releaseTimes.stream()
                .map(ReleaseTime::of)
                .toList();
        return new ReleaseTimes(releaseTimeList);
    }

    @Override
    public String toString() {
        return releaseTimes.stream()
                .map(releaseTime -> releaseTime.toLocalTime().format(ReleaseTime.FORMATTER))
                .collect(Collectors.joining(";"));
    }
}
