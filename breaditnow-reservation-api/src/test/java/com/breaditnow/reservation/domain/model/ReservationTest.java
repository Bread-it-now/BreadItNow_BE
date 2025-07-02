package com.breaditnow.reservation.domain.model;

import com.breaditnow.common.domain.Money;
import com.breaditnow.common.domain.ReservationStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Reservation 도메인 단위 테스트")
class ReservationTest {

    private final Orderer orderer = new Orderer(1L, "홍길동", "010-1234-5678");
    private final ReservedBakery reservedBakery = new ReservedBakery(
            1L, "테스트베이커리", "서울시 강남구", "02-123-4567", "img.jpg"
    );
    private final ReservationProduct product1 = new ReservationProduct(
            1L, "식빵", "bread.jpg", new Money(3000), 2
    );
    private final ReservationProduct product2 = new ReservationProduct(
            2L, "크루아상", "croissant.jpg", new Money(2000), 1
    );
    private final List<ReservationProduct> products = List.of(product1, product2);

    @Nested
    @DisplayName("생성자 테스트")
    class ConstructorTest {
        @Test
        @DisplayName("정상적으로 예약을 생성한다")
        void createReservation() {
            Reservation reservation = new Reservation(orderer, reservedBakery, products);

            assertThat(reservation.getOrderer()).isEqualTo(orderer);
            assertThat(reservation.getReservedBakery()).isEqualTo(reservedBakery);
            assertThat(reservation.getReservationProducts()).containsExactly(product1, product2);
            assertThat(reservation.getReservationState().getReservationStatus()).isEqualTo(ReservationStatus.WAITING);
            assertThat(reservation.getTotalPrice()).isEqualTo(new Money(8000));
        }
    }

    @Nested
    @DisplayName("상태 변경 테스트")
    class StateChangeTest {
        @Test
        @DisplayName("예약 승인 시 상태와 예약번호가 변경된다")
        void approve() {
            Reservation reservation = new Reservation(orderer, reservedBakery, products);
            reservation.approve(123L);

            assertThat(reservation.getReservationState().getReservationStatus()).isEqualTo(ReservationStatus.APPROVED);
            assertThat(reservation.getReservationNumber()).isEqualTo(123L);
        }

        @Test
        @DisplayName("예약 부분승인 시 상품, 예약번호, 총액이 변경된다")
        void partialApprove() {
            Reservation reservation = new Reservation(orderer, reservedBakery, products);
            ReservationProduct onlyOne = new ReservationProduct(
                    1L, "식빵", "bread.jpg", new Money(3000), 1
            );
            reservation.partialApprove(List.of(onlyOne), 456L);

            assertThat(reservation.getReservationState().getReservationStatus()).isEqualTo(ReservationStatus.PARTIAL_APPROVED);
            assertThat(reservation.getReservationProducts()).containsExactly(onlyOne);
            assertThat(reservation.getReservationNumber()).isEqualTo(456L);
            assertThat(reservation.getTotalPrice()).isEqualTo(new Money(3000));
        }

        @Test
        @DisplayName("예약 취소 시 상태가 취소됨으로 변경된다")
        void cancel() {
            Reservation reservation = new Reservation(orderer, reservedBakery, products);
            reservation.cancel("고객 요청");

            assertThat(reservation.getReservationState().getReservationStatus()).isEqualTo(ReservationStatus.CANCELLED);
            assertThat(reservation.getReservationState().getCancelReason()).isEqualTo("고객 요청");
        }
    }

    @Nested
    @DisplayName("날짜 계산 테스트")
    class DateTest {
        @Test
        @DisplayName("예약 완료 상태에서 픽업 마감시간을 반환한다")
        void getPickupDeadline() {
            LocalDateTime now = LocalDateTime.now();
            Reservation reservation = Reservation.builder()
                    .reservationId(1L)
                    .reservationNumber(1L)
                    .reservationProducts(products)
                    .reservedBakery(reservedBakery)
                    .orderer(orderer)
                    .reservationState(new ReservationState(ReservationStatus.APPROVED, null))
                    .totalPrice(new Money(8000))
                    .reservationTime(now)
                    .build();

            String deadline = reservation.getPickupDeadline();
            assertThat(deadline).isEqualTo(now.plusMinutes(30).format(com.breaditnow.common.domain.DailyTime.DATE_FORMATTER));
        }

        @Test
        @DisplayName("예약시간이 없거나 완료상태가 아니면 픽업 마감시간은 null")
        void getPickupDeadline_null() {
            Reservation reservation = new Reservation(orderer, reservedBakery, products);
            assertThat(reservation.getPickupDeadline()).isNull();
        }
    }

    @Test
    @DisplayName("예약일시 포맷 반환")
    void getReservationDate() {
        LocalDateTime now = LocalDateTime.now();
        Reservation reservation = Reservation.builder()
                .reservationTime(now)
                .reservationState(new ReservationState(ReservationStatus.APPROVED, null))
                .reservationProducts(products)
                .reservedBakery(reservedBakery)
                .orderer(orderer)
                .totalPrice(new Money(8000))
                .build();

        assertThat(reservation.getReservationDate()).isEqualTo(now.format(com.breaditnow.common.domain.DailyTime.DATE_FORMATTER));
    }
}