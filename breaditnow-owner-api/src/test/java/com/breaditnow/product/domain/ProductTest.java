package com.breaditnow.product.domain;

import com.breaditnow.common.domain.DailyTime;
import com.breaditnow.common.exception.OwnerException;
import com.breaditnow.image.domain.Image;
import com.breaditnow.product.domain.model.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.breaditnow.common.exception.OwnerErrorCode.INVALID_STOCK;
import static com.breaditnow.common.exception.OwnerErrorCode.PRODUCT_NOT_FOUND;
import static org.assertj.core.api.Assertions.*;

@DisplayName("Product 도메인 상세 테스트")
class ProductTest {

    private Product createProduct(Long id, boolean deleted) {
        ProductInfo info = ProductInfo.create("식빵", "맛있는 식빵", new Image("url"));
        SalesPolicy policy = SalesPolicy.create(1000).withStock(5);
        Classification classification = Classification.create(ProductType.BREAD);
        List<DailyTime> releaseTimes = List.of(DailyTime.of("09:00"), DailyTime.of("15:00"));
        return Product.builder()
                .id(id)
                .bakeryId(10L)
                .productInfo(info)
                .displayOrder(1)
                .salesPolicy(policy)
                .classification(classification)
                .releaseTimes(releaseTimes)
                .deleted(deleted)
                .build();
    }

    @Test
    @DisplayName("정상 생성 및 getter 동작")
    void createAndGetters() {
        Product product = createProduct(1L, false);
        assertThat(product.getId()).isEqualTo(1L);
        assertThat(product.getBakeryId()).isEqualTo(10L);
        assertThat(product.getProductInfo().name()).isEqualTo("식빵");
        assertThat(product.getDisplayOrder()).isEqualTo(1);
        assertThat(product.getSalesPolicy().stock()).isEqualTo(5);
        assertThat(product.getClassification().type()).isEqualTo(ProductType.BREAD);
        assertThat(product.getReleaseTimes()).hasSize(2);
        assertThat(product.isDeleted()).isFalse();
    }

    @Test
    @DisplayName("정적 팩토리 create로 생성")
    void createStaticFactory() {
        ProductInfo info = ProductInfo.create("크루아상", "버터향 가득", new Image("url2"));
        SalesPolicy policy = SalesPolicy.create(2000).withStock(10);
        Classification classification = Classification.create(ProductType.BREAD);
        List<DailyTime> releaseTimes = List.of(DailyTime.of("10:00"));
        Product product = Product.create(
                20L, info, 2, policy, classification, releaseTimes
        );
        assertThat(product.getBakeryId()).isEqualTo(20L);
        assertThat(product.getDisplayOrder()).isEqualTo(2);
        assertThat(product.getSalesPolicy().stock()).isEqualTo(10);
        assertThat(product.isDeleted()).isFalse();
    }

    @Test
    @DisplayName("update로 정보 변경")
    void updateProduct() {
        Product product = createProduct(1L, false);
        ProductInfo newInfo = ProductInfo.create("단팥빵", "달콤한 단팥", new Image("url3"));
        SalesPolicy newPolicy = SalesPolicy.create(1500).withStock(99);
        Classification newClass = Classification.create(ProductType.BREAD);
        List<DailyTime> newTimes = List.of(DailyTime.of("18:00"));
        product.update(newInfo, newPolicy, newClass, newTimes);
        assertThat(product.getProductInfo()).isEqualTo(newInfo);
        assertThat(product.getSalesPolicy()).isEqualTo(newPolicy);
        assertThat(product.getClassification()).isEqualTo(newClass);
        assertThat(product.getReleaseTimes()).isEqualTo(newTimes);
    }

    @Test
    @DisplayName("updateStock 정상 동작")
    void updateStock() {
        Product product = createProduct(1L, false);
        product.updateStock(123);
        assertThat(product.getSalesPolicy().stock()).isEqualTo(123);
    }

    @Test
    @DisplayName("updateStock 음수 입력시 예외")
    void updateStockNegative() {
        Product product = createProduct(1L, false);
        assertThatThrownBy(() -> product.updateStock(-1))
                .isInstanceOf(OwnerException.class)
                .hasFieldOrPropertyWithValue("errorCode", INVALID_STOCK);
    }

    @Test
    @DisplayName("changeStatus 정상 동작")
    void changeStatus() {
        Product product = createProduct(1L, false);
        product.changeStatus(ProductStatus.SOLD_OUT);
        assertThat(product.getSalesPolicy().status()).isEqualTo(ProductStatus.SOLD_OUT);
    }

    @Test
    @DisplayName("updateDisplayOrder 정상 동작")
    void updateDisplayOrder() {
        Product product = createProduct(1L, false);
        product.updateDisplayOrder(7);
        assertThat(product.getDisplayOrder()).isEqualTo(7);
    }

    @Test
    @DisplayName("delete 정상 동작")
    void delete() {
        Product product = createProduct(1L, false);
        product.delete();
        assertThat(product.isDeleted()).isTrue();
    }

    @Test
    @DisplayName("validateBelongsTo 정상/예외")
    void validateBelongsTo() {
        Product product = createProduct(1L, false);
        product.validateBelongsTo(10L); // 정상
        assertThatThrownBy(() -> product.validateBelongsTo(99L))
                .isInstanceOf(OwnerException.class)
                .hasFieldOrPropertyWithValue("errorCode", PRODUCT_NOT_FOUND);
    }

    @Test
    @DisplayName("validateIsActive 정상/예외")
    void validateIsActive() {
        Product product = createProduct(1L, false);
        product.validateIsActive(); // 정상
        Product deleted = createProduct(2L, true);
        assertThatThrownBy(deleted::validateIsActive)
                .isInstanceOf(OwnerException.class)
                .hasFieldOrPropertyWithValue("errorCode", PRODUCT_NOT_FOUND);
    }

    @Test
    @DisplayName("삭제된 상품에 대해 상태 변경/수정/삭제 시 예외")
    void throwIfDeleted() {
        Product deleted = createProduct(1L, true);
        assertThatThrownBy(() -> deleted.updateDisplayOrder(2)).isInstanceOf(OwnerException.class);
        assertThatThrownBy(() -> deleted.updateStock(1)).isInstanceOf(OwnerException.class);
        assertThatThrownBy(() -> deleted.changeStatus(ProductStatus.FOR_SALE)).isInstanceOf(OwnerException.class);
        assertThatThrownBy(() -> deleted.update(
                ProductInfo.create("a", "b", new Image("u")),
                SalesPolicy.create(1),
                Classification.create(ProductType.BREAD),
                List.of(DailyTime.of("12:00"))
        )).isInstanceOf(OwnerException.class);
        assertThatThrownBy(deleted::delete).isInstanceOf(OwnerException.class);
    }

    @Test
    @DisplayName("getReleaseTimesAsString 정상 동작")
    void getReleaseTimesAsString() {
        Product product = createProduct(1L, false);
        assertThat(product.getReleaseTimesAsString()).containsExactly("09:00", "15:00");
        Product empty = Product.builder()
                .id(2L)
                .bakeryId(1L)
                .productInfo(ProductInfo.create("a", "b", new Image("u")))
                .displayOrder(1)
                .salesPolicy(SalesPolicy.create(1))
                .classification(Classification.create(ProductType.BREAD))
                .releaseTimes(null)
                .deleted(false)
                .build();
        assertThat(empty.getReleaseTimesAsString()).isEmpty();
    }
}