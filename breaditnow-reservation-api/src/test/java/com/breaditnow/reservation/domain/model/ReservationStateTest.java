package com.breaditnow.reservation.domain.model;

import com.breaditnow.common.exception.ReservationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.breaditnow.common.domain.ReservationStatus.*;
import static com.breaditnow.common.exception.ReservationErrorCode.ALREADY_PROCESSED;
import static com.breaditnow.common.exception.ReservationErrorCode.CANCELLATION_REASON_REQUIRED;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DisplayName("ReservationState 단위 테스트")
class ReservationStateTest {

    @Nested
    @DisplayName("승인/부분승인 테스트")
    class ApproveTest {
        @Test
        @DisplayName("WAITING 상태에서 승인하면 APPROVED로 변경된다")
        void approve_success() {
            ReservationState state = ReservationState.waiting();
            state.approve();
            assertThat(state.getReservationStatus()).isEqualTo(APPROVED);
        }

        @Test
        @DisplayName("WAITING 상태에서 부분승인하면 PARTIAL_APPROVED로 변경된다")
        void partialApprove_success() {
            ReservationState state = ReservationState.waiting();
            state.partiallyApprove();
            assertThat(state.getReservationStatus()).isEqualTo(PARTIAL_APPROVED);
        }

        @Test
        @DisplayName("WAITING이 아닌 상태에서 approve/partialApprove 시 예외 발생")
        void approve_alreadyProcessed() {
            ReservationState state = new ReservationState(APPROVED, null);
            assertThatThrownBy(state::approve)
                    .isInstanceOf(ReservationException.class)
                    .hasFieldOrPropertyWithValue("errorCode", ALREADY_PROCESSED);

            ReservationState state2 = new ReservationState(PARTIAL_APPROVED, null);
            assertThatThrownBy(state2::partiallyApprove)
                    .isInstanceOf(ReservationException.class)
                    .hasFieldOrPropertyWithValue("errorCode", ALREADY_PROCESSED);
        }
    }

    @Nested
    @DisplayName("취소 테스트")
    class CancelTest {
        @Test
        @DisplayName("WAITING 상태에서 취소하면 CANCELLED로 변경되고 사유가 저장된다")
        void cancel_success() {
            ReservationState state = ReservationState.waiting();
            state.cancel("고객 요청");
            assertThat(state.getReservationStatus()).isEqualTo(CANCELLED);
            assertThat(state.getCancelReason()).isEqualTo("고객 요청");
        }

        @Test
        @DisplayName("이미 완료된 상태에서 취소 시 예외 발생")
        void cancel_alreadyProcessed() {
            ReservationState state = new ReservationState(APPROVED, null);
            assertThatThrownBy(() -> state.cancel("사유"))
                    .isInstanceOf(ReservationException.class)
                    .hasFieldOrPropertyWithValue("errorCode", ALREADY_PROCESSED);
        }

        @Test
        @DisplayName("취소 사유가 null이면 예외 발생")
        void cancel_reasonRequired() {
            ReservationState state = ReservationState.waiting();
            assertThatThrownBy(() -> state.cancel(null))
                    .isInstanceOf(ReservationException.class)
                    .hasFieldOrPropertyWithValue("errorCode", CANCELLATION_REASON_REQUIRED);
        }
    }

    @Test
    @DisplayName("isCompleted는 APPROVED, PARTIAL_APPROVED에서 true")
    void isCompleted() {
        assertThat(new ReservationState(APPROVED, null).isCompleted()).isTrue();
        assertThat(new ReservationState(PARTIAL_APPROVED, null).isCompleted()).isTrue();
        assertThat(new ReservationState(WAITING, null).isCompleted()).isFalse();
        assertThat(new ReservationState(CANCELLED, "사유").isCompleted()).isFalse();
    }
}