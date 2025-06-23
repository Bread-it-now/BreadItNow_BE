package com.breaditnow.customer.reservation.domain;

import com.breaditnow.customer.common.domain.Money;
import com.breaditnow.customer.common.exception.CustomerException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.breaditnow.customer.common.exception.CustomerErrorCode.UNAUTHORIZED_RESERVATION_CANCEL;
import static com.breaditnow.customer.reservation.domain.ReservationStatus.CANCELLED;
import static com.breaditnow.customer.reservation.domain.ReservationStatus.WAITING;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DisplayName("Reservation 도메인 테스트")
class ReservationTest {

    private ReservationProduct createProduct(long id, int price, int quantity) {
        return new ReservationProduct(id, "상품" + id, "image.jpg", new Money(price), quantity);
    }

    @Nested
    @DisplayName("생성")
    class Creation {

        @Test
        @DisplayName("필수값만으로 생성 시 대기 상태, 현재 시간, 총액이 자동 설정된다")
        void createWithRequiredValues() {
            List<ReservationProduct> products = List.of(
                    createProduct(1L, 1000, 2),
                    createProduct(2L, 2000, 1)
            );
            Reservation reservation = new Reservation(10L, 20L, products);

            assertThat(reservation.getOrdererId()).isEqualTo(10L);
            assertThat(reservation.getBakeryId()).isEqualTo(20L);
            assertThat(reservation.getReservationProducts()).isEqualTo(products);
            assertThat(reservation.getReservationState().getReservationStatus()).isEqualTo(WAITING);
            assertThat(reservation.getReservationTime()).isNotNull();
            assertThat(reservation.getTotalPrice()).isEqualTo(new Money(4000)); // 1000*2 + 2000*1
        }
    }

    @Nested
    @DisplayName("취소")
    class Cancel {

        @Test
        @DisplayName("주문자가 맞으면 정상적으로 취소된다")
        void cancelSuccessfully() {
            Reservation reservation = new Reservation(1L, 2L, List.of(createProduct(1L, 1000, 1)));
            reservation.cancel(1L, "사유");

            assertThat(reservation.getReservationState().getReservationStatus()).isEqualTo(CANCELLED);
            assertThat(reservation.getReservationState().getCancelReason()).isEqualTo("사유");
        }

        @Test
        @DisplayName("주문자가 다르면 예외가 발생한다")
        void throwExceptionWhenOrdererIdIsNotMatched() {
            Reservation reservation = new Reservation(1L, 2L, List.of(createProduct(1L, 1000, 1)));

            assertThatThrownBy(() -> reservation.cancel(999L, "사유"))
                    .isInstanceOf(CustomerException.class)
                    .hasFieldOrPropertyWithValue("errorCode", UNAUTHORIZED_RESERVATION_CANCEL);
        }
    }
}