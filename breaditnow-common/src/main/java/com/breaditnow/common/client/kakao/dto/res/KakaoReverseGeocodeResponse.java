package com.breaditnow.common.client.kakao.dto.res;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class KakaoReverseGeocodeResponse {

    private List<Document> documents;

    @Setter
    @Getter
    public static class Document {
        private Address address;
    }

    @Setter
    @Getter
    public static class Address {
        @JsonProperty("region_1depth_name")
        private String sidoName;

        @JsonProperty("region_2depth_name")
        private String gugunName;

        @JsonProperty("region_3depth_name")
        private String dongName;
    }
}


