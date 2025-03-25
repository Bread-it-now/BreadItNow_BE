package com.breaditnow.customer.domain.customer.controller.res;

public record NicknameDuplicateResponse(boolean duplicated) {

    public static NicknameDuplicateResponse of(boolean duplicated) {
        return new NicknameDuplicateResponse(duplicated);
    }
}
