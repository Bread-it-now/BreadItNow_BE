//package com.breaditnow.customer.reservation.domain;
//
//import com.amazonaws.services.ec2.model.ReservationState;
//import com.breaditnow.common.exception.CustomerException;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Nested;
//import org.junit.jupiter.api.Test;
//
//import static com.breaditnow.common.domain.ReservationStatus.CANCELLED;
//import static org.assertj.core.api.Assertions.assertThatThrownBy;
//
//@DisplayName("ReservationState 도메인 테스트")
//class ReservationStateTest {
//
//    @Nested
//    @DisplayName("생성")
//    class Creation {
//
//        @Test
//        @DisplayName("예약 상태와 취소 사유로 생성할 수 있다")
//        void createWithStatusAndReason() {
//            ReservationState state = new ReservationState(CANCELLED, "사유");
//            assertThat(state.getReservationStatus()).isEqualTo(CANCELLED);
//            assertThat(state.getCancelReason()).isEqualTo("사유");
//        }
//
//        @Test
//        @DisplayName("waiting() 팩토리 메서드는 대기 상태로 생성한다")
//        void createWaiting() {
//            ReservationState state = ReservationState.waiting();
//            assertThat(state.getReservationStatus()).isEqualTo(WAITING);
//            assertThat(state.getCancelReason()).isNull();
//        }
//    }
//
//    @Nested
//    @DisplayName("취소")
//    class Cancel {
//
//        @Test
//        @DisplayName("정상적으로 취소하면 상태와 사유가 변경된다")
//        void cancelSuccessfully() {
//            ReservationState state = ReservationState.waiting();
//            state.cancel("고객 요청");
//
//            assertThat(state.getReservationStatus()).isEqualTo(CANCELLED);
//            assertThat(state.getCancelReason()).isEqualTo("고객 요청");
//        }
//
//        @Test
//        @DisplayName("취소 사유가 비어있으면 예외가 발생한다")
//        void throwExceptionWhenCancelReasonIsEmpty() {
//            ReservationState state = ReservationState.waiting();
//
//            assertThatThrownBy(() -> state.cancel(""))
//                    .isInstanceOf(CustomerException.class)
//                    .hasFieldOrPropertyWithValue("errorCode", CANCELLATION_REASON_REQUIRED);
//        }
//
//        @Test
//        @DisplayName("이미 취소된 상태에서 다시 취소하면 예외가 발생한다")
//        void throwExceptionWhenAlreadyCancelled() {
//            ReservationState state = new ReservationState(CANCELLED, "이미 취소됨");
//
//            assertThatThrownBy(() -> state.cancel("다시 취소"))
//                    .isInstanceOf(CustomerException.class)
//                    .hasFieldOrPropertyWithValue("errorCode", ALREADY_CANCELLED);
//        }
//    }
//}