package com.breaditnow.bakery.presentation.response;

import com.breaditnow.bakery.domain.OperatingStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BakeryResponse {
    private Long bakeryId;
    private String name;
    private String address;
    private String phone;
    private String openTime;
    private String introduction;
    private String profileImage;
    private String[] additionalImages;
    private OperatingStatus operatingStatus;
    private Boolean isBakeryFavorite;
}
