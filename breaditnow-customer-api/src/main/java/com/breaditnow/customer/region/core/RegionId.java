package com.breaditnow.customer.region.core;

public record RegionId(String sidoCode, String gugunCode, String dongCode) {
    public RegionId(String sidoCode, String gugunCode, String dongCode) {
        this.sidoCode = defaultIfEmpty(sidoCode);
        this.gugunCode = defaultIfEmpty(gugunCode);
        this.dongCode = defaultIfEmpty(dongCode);
    }

    private String defaultIfEmpty(String code) {
        return (code == null || code.isBlank()) ? "000" : code;
    }
}
