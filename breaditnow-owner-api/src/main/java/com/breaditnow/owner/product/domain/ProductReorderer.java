package com.breaditnow.owner.product.domain;

import com.breaditnow.owner.global.exception.OwnerException;
import com.breaditnow.owner.product.infrastructure.presentation.request.ProductDisplayOrderUpdateRequest.ProductOrder;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.breaditnow.owner.global.exception.OwnerErrorCode.*;

public class ProductReorderer {
    public void reorder(List<Product> productsToUpdate, List<ProductOrder> requestedOrders) {
        validate(productsToUpdate, requestedOrders);

        Map<Long, Product> productMap = productsToUpdate.stream()
                .collect(Collectors.toMap(Product::getId, product -> product));

        requestedOrders.forEach(orderInfo -> {
            Product product = productMap.get(orderInfo.productId());
            product.updateDisplayOrder(orderInfo.displayOrder());
        });
    }

    private void validate(List<Product> productsToUpdate, List<ProductOrder> requestedOrders) {
        long distinctIdCount = requestedOrders.stream().map(ProductOrder::productId).distinct().count();
        if (requestedOrders.size() != distinctIdCount) {
            throw new OwnerException(DUPLICATE_PRODUCT_ID_IN_REQUEST);
        }

        List<Integer> newDisplayOrders = requestedOrders.stream().map(ProductOrder::displayOrder).toList();
        long distinctOrderCount = new HashSet<>(newDisplayOrders).size();
        if (newDisplayOrders.size() != distinctOrderCount) {
            throw new OwnerException(DUPLICATE_DISPLAY_ORDER);
        }

        Set<Integer> originalOrders = productsToUpdate.stream()
                .map(Product::getDisplayOrder)
                .collect(Collectors.toSet());

        Set<Integer> newOrdersSet = new HashSet<>(newDisplayOrders);
        if (!originalOrders.equals(newOrdersSet)) {
            throw new OwnerException(DISPLAY_ORDER_SET_MISMATCH);
        }
    }
}
