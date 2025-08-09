package com.breaditnow.bakery.presentation.response;

import com.breaditnow.bakery.domain.OperatingStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HotBakeryResponse {
    private Long bakeryId;
    private String bakeryName;
    private String profileImage;
    private Double distance;
    private OperatingStatus operatingStatus;
    private Integer favoriteCount;
    private Long reservationCount;
    private Boolean isFavorite;
}
