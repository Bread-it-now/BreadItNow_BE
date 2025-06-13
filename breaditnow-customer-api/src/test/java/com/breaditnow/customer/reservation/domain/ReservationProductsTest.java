package com.breaditnow.customer.reservation.domain;

import com.breaditnow.customer.common.domain.Money;
import com.breaditnow.customer.common.exception.CustomerException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.breaditnow.customer.common.exception.CustomerErrorCode.QUANTITY_POSITIVE;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DisplayName("ReservationProducts 도메인 테스트")
class ReservationProductsTest {

    @Nested
    @DisplayName("생성")
    class Creation {

        @Test
        @DisplayName("정상적으로 생성된다")
        void createSuccessfully() {
            Money price = new Money(1000);
            int quantity = 3;

            ReservationProducts products = new ReservationProducts(1L, "식빵", "image.jpg", price, quantity);

            assertThat(products.getProductId()).isEqualTo(1L);
            assertThat(products.getProductName()).isEqualTo("식빵");
            assertThat(products.getProductImage()).isEqualTo("image.jpg");
            assertThat(products.getPrice()).isEqualTo(price);
            assertThat(products.getQuantity()).isEqualTo(quantity);
            assertThat(products.getTotalPrice()).isEqualTo(new Money(3000));
        }

        @ParameterizedTest
        @ValueSource(ints = {0, -1, -10})
        @DisplayName("수량이 0 이하이면 예외가 발생한다")
        void throwExceptionWhenQuantityIsNotPositive(int quantity) {
            Money price = new Money(1000);

            assertThatThrownBy(() -> new ReservationProducts(1L, "식빵", "image.jpg", price, quantity))
                    .isInstanceOf(CustomerException.class)
                    .hasFieldOrPropertyWithValue("errorCode", QUANTITY_POSITIVE);
        }
    }

    @Test
    @DisplayName("총액은 가격 * 수량으로 계산된다")
    void calculateTotalPrice() {
        Money price = new Money(2500);
        int quantity = 2;

        ReservationProducts products = new ReservationProducts(2L, "크루아상", "croissant.jpg", price, quantity);

        assertThat(products.getTotalPrice()).isEqualTo(new Money(5000));
    }
}