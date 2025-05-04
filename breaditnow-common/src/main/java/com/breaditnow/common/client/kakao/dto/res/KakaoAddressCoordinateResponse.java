package com.breaditnow.common.client.kakao.dto.res;

import com.breaditnow.common.client.kakao.dto.AddressCoordinateDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class KakaoAddressCoordinateResponse {

    @JsonProperty("documents")
    private List<Document> documents;

    @Getter
    @Setter
    public static class Document {
        @JsonProperty("x")
        private String longitude;

        @JsonProperty("y")
        private String latitude;
    }

    public AddressCoordinateDto getFirstDocumentCoordinates() {
        if (documents != null && !documents.isEmpty()) {
            Document firstDocument = documents.get(0);
            return new AddressCoordinateDto(firstDocument.getLatitude(), firstDocument.getLongitude());
        }
        return null;
    }
}
