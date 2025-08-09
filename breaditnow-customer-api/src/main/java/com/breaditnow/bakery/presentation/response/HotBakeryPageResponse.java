package com.breaditnow.bakery.presentation.response;

import com.breaditnow.common.domain.PageInfo;
import org.springframework.data.domain.Page;

import java.util.List;

public record HotBakeryPageResponse(List<HotBakeryResponse> hotBakeries, PageInfo pageInfo) {
    public static HotBakeryPageResponse of(Page<HotBakeryResponse> hotBakeryResponses) {
        return new HotBakeryPageResponse(hotBakeryResponses.getContent(), PageInfo.of(hotBakeryResponses));
    }
}
