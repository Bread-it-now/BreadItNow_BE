package com.breaditnow.customer.location.infrastructure;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class KakaoAddressResponse {

    private List<Document> documents;

    @Getter
    @NoArgsConstructor
    public static class Document {
        private Address address;
    }

    @Getter
    @NoArgsConstructor
    public static class Address {
        @JsonProperty("region_1depth_name")
        private String region1depthName;

        @JsonProperty("region_2depth_name")
        private String region2depthName;

        @JsonProperty("region_3depth_name")
        private String region3depthName;
    }
}
