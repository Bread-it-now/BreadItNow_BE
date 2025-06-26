package com.breaditnow.product.domain;

import com.breaditnow.common.exception.OwnerException;
import com.breaditnow.product.adapter.in.dto.request.ProductDisplayOrderUpdateRequest;
import com.breaditnow.product.domain.model.Product;
import com.breaditnow.product.domain.model.ProductReorderer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static com.breaditnow.common.exception.OwnerErrorCode.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("ProductReorderer 도메인 테스트")
class ProductReordererTest {

    private ProductReorderer reorderer;

    @BeforeEach
    void setUp() {
        reorderer = new ProductReorderer();
    }

    private Product createProduct(Long id, int displayOrder) {
        return Product.builder()
                .id(id)
                .bakeryId(1L)
                .productInfo(null)
                .displayOrder(displayOrder)
                .salesPolicy(null)
                .classification(null)
                .releaseTimes(Collections.emptyList())
                .deleted(false)
                .build();
    }

    @Test
    @DisplayName("정상적으로 순서를 변경한다")
    void reorderSuccessfully() {
        Product p1 = createProduct(1L, 1);
        Product p2 = createProduct(2L, 2);
        Product p3 = createProduct(3L, 3);

        List<Product> products = List.of(p1, p2, p3);
        List<ProductDisplayOrderUpdateRequest.ProductOrder> orders = List.of(
                new ProductDisplayOrderUpdateRequest.ProductOrder(1L, 3),
                new ProductDisplayOrderUpdateRequest.ProductOrder(2L, 1),
                new ProductDisplayOrderUpdateRequest.ProductOrder(3L, 2)
        );

        reorderer.reorder(products, orders);

        assertThat(p1.getDisplayOrder()).isEqualTo(3);
        assertThat(p2.getDisplayOrder()).isEqualTo(1);
        assertThat(p3.getDisplayOrder()).isEqualTo(2);
    }

    @Test
    @DisplayName("productId 중복 시 예외 발생")
    void throwWhenDuplicateProductId() {
        Product p1 = createProduct(1L, 1);
        Product p2 = createProduct(2L, 2);

        List<Product> products = List.of(p1, p2);
        List<ProductDisplayOrderUpdateRequest.ProductOrder> orders = List.of(
                new ProductDisplayOrderUpdateRequest.ProductOrder(1L, 2),
                new ProductDisplayOrderUpdateRequest.ProductOrder(1L, 1)
        );

        assertThatThrownBy(() -> reorderer.reorder(products, orders))
                .isInstanceOf(OwnerException.class)
                .hasFieldOrPropertyWithValue("errorCode", DUPLICATE_PRODUCT_ID_IN_REQUEST);
    }

    @Test
    @DisplayName("displayOrder 중복 시 예외 발생")
    void throwWhenDuplicateDisplayOrder() {
        Product p1 = createProduct(1L, 1);
        Product p2 = createProduct(2L, 2);

        List<Product> products = List.of(p1, p2);
        List<ProductDisplayOrderUpdateRequest.ProductOrder> orders = List.of(
                new ProductDisplayOrderUpdateRequest.ProductOrder(1L, 1),
                new ProductDisplayOrderUpdateRequest.ProductOrder(2L, 1)
        );

        assertThatThrownBy(() -> reorderer.reorder(products, orders))
                .isInstanceOf(OwnerException.class)
                .hasFieldOrPropertyWithValue("errorCode", DUPLICATE_DISPLAY_ORDER);
    }

    @Test
    @DisplayName("displayOrder 집합 불일치 시 예외 발생")
    void throwWhenDisplayOrderSetMismatch() {
        Product p1 = createProduct(1L, 1);
        Product p2 = createProduct(2L, 2);

        List<Product> products = List.of(p1, p2);
        List<ProductDisplayOrderUpdateRequest.ProductOrder> orders = List.of(
                new ProductDisplayOrderUpdateRequest.ProductOrder(1L, 3),
                new ProductDisplayOrderUpdateRequest.ProductOrder(2L, 4)
        );

        assertThatThrownBy(() -> reorderer.reorder(products, orders))
                .isInstanceOf(OwnerException.class)
                .hasFieldOrPropertyWithValue("errorCode", DISPLAY_ORDER_SET_MISMATCH);
    }
}