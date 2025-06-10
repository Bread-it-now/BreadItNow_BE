package com.breaditnow.customer.alert.domain;

import com.breaditnow.customer.common.exception.CustomerException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.breaditnow.customer.common.exception.CustomerErrorCode.ALREADY_FAVORITED;
import static com.breaditnow.customer.common.exception.CustomerErrorCode.NOT_FAVORITED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("ProductAlert 도메인 테스트")
class ProductAlertTest {

    @Nested
    @DisplayName("생성 테스트")
    class Creation {

        @Test
        @DisplayName("create로 생성 시 isActive는 true")
        void createActive() {
            ProductAlert alert = ProductAlert.create(1L, 2L);
            assertThat(alert.getCustomerId()).isEqualTo(1L);
            assertThat(alert.getProductId()).isEqualTo(2L);
            assertThat(alert.isActive()).isTrue();
        }

        @Test
        @DisplayName("from으로 생성 시 isActive 값이 반영된다")
        void fromWithActive() {
            ProductAlert alert = ProductAlert.from(1L, 2L, false);
            assertThat(alert.isActive()).isFalse();
        }
    }

    @Nested
    @DisplayName("상태 토글 테스트")
    class Toggle {

        @Test
        @DisplayName("toggle 호출 시 isActive가 반전된다")
        void toggleActive() {
            ProductAlert alert = ProductAlert.from(1L, 2L, true);
            alert.toggle();
            assertThat(alert.isActive()).isFalse();
            alert.toggle();
            assertThat(alert.isActive()).isTrue();
        }
    }

    @Nested
    @DisplayName("활성화/비활성화 테스트")
    class ActivateDeactivate {

        @Test
        @DisplayName("이미 활성화된 상태에서 activate 호출 시 예외 발생")
        void activateAlreadyActive() {
            ProductAlert alert = ProductAlert.from(1L, 2L, true);
            assertThatThrownBy(alert::activate)
                    .isInstanceOf(CustomerException.class)
                    .hasFieldOrPropertyWithValue("errorCode", ALREADY_FAVORITED);
        }

        @Test
        @DisplayName("비활성 상태에서 activate 호출 시 활성화된다")
        void activateFromInactive() {
            ProductAlert alert = ProductAlert.from(1L, 2L, false);
            alert.activate();
            assertThat(alert.isActive()).isTrue();
        }

        @Test
        @DisplayName("이미 비활성화된 상태에서 deactivate 호출 시 예외 발생")
        void deactivateAlreadyInactive() {
            ProductAlert alert = ProductAlert.from(1L, 2L, false);
            assertThatThrownBy(alert::deactivate)
                    .isInstanceOf(CustomerException.class)
                    .hasFieldOrPropertyWithValue("errorCode", NOT_FAVORITED);
        }

        @Test
        @DisplayName("활성 상태에서 deactivate 호출 시 비활성화된다")
        void deactivateFromActive() {
            ProductAlert alert = ProductAlert.from(1L, 2L, true);
            alert.deactivate();
            assertThat(alert.isActive()).isFalse();
        }
    }
}