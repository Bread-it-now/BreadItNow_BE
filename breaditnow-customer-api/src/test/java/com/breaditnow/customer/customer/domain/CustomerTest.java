package com.breaditnow.customer.customer.domain;

import com.breaditnow.customer.common.exception.CustomerException;
import com.breaditnow.customer.customer.domain.port.SaveImageStoragePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@DisplayName("Customer 도메인 테스트")
class CustomerTest {
    private SaveImageStoragePort saveImageStoragePort;
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        saveImageStoragePort = mock(SaveImageStoragePort.class);
        passwordEncoder = mock(PasswordEncoder.class);
    }

    @Nested
    @DisplayName("닉네임 변경")
    class ChangeNickname {

        @Test
        @DisplayName("초기 설정이 완료된 경우 닉네임 변경 시 예외 발생")
        void throwExceptionWhenInitialSetupIsTrue() {
            // given
            Customer customer = Customer.builder()
                    .nickname("nickname")
                    .initialSetup(true)
                    .build();

            // when & then
            assertThatThrownBy(() -> customer.changeNickname("newNick"))
                    .isInstanceOf(CustomerException.class);
        }

        @Test
        @DisplayName("빈 닉네임으로 변경 시 예외 발생")
        void throwExceptionWhenNicknameIsEmpty() {
            // given
            Customer customer = Customer.builder()
                    .nickname("nickname")
                    .initialSetup(false)
                    .build();

            // when & then
            assertThatThrownBy(() -> customer.changeNickname(""))
                    .isInstanceOf(CustomerException.class);
        }

        @Test
        @DisplayName("유효한 닉네임으로 변경 시 닉네임이 변경되고 초기 설정 플래그가 true로 변경됨")
        void changeNicknameSuccessfully() {
            // given
            Customer customer = Customer.builder()
                    .initialSetup(false)
                    .build();

            // when
            customer.changeNickname("newNick");

            // then
            assertThat(customer.getNickname()).isEqualTo("newNick");
            assertThat(customer.isInitialSetup()).isTrue();
        }
    }
}