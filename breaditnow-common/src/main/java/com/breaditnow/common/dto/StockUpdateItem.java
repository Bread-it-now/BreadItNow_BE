package com.breaditnow.common.dto;

public record StockUpdateItem(
        Long productId,
        int quantity
) {
}
