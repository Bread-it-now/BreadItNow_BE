package com.breaditnow.bakery.presentation.response;

import com.breaditnow.bakery.domain.OperatingStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BakeryFavoriteResponse {
    private Long bakeryId;
    private String bakeryName;
    private String profileImage;
    private Double distance;
    private OperatingStatus operatingStatus;
    private Integer favoriteCount;
    private boolean isBakeryActive;
}
