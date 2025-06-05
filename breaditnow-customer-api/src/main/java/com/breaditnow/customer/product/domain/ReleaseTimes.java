package com.breaditnow.customer.product.domain;

import com.breaditnow.customer.common.domain.ReleaseTime;
import io.micrometer.common.util.StringUtils;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ReleaseTimes {
    private List<ReleaseTime> releaseTimes = new ArrayList<>();

    public ReleaseTimes(List<ReleaseTime> releaseTimes) {
        this.releaseTimes = releaseTimes;
    }

    public static ReleaseTimes of(String releaseTimesStr) {
        if (StringUtils.isEmpty(releaseTimesStr)) return new ReleaseTimes(List.of());
        List<ReleaseTime> releaseTimeList = Arrays.stream(releaseTimesStr.split(";"))
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
