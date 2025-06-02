package com.breaditnow.customer.customer.domain;

import com.breaditnow.customer.common.exception.CustomerException;
import com.breaditnow.customer.customer.domain.port.SaveImageStoragePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class CustomerTest {
    private SaveImageStoragePort saveImageStoragePort;
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        saveImageStoragePort = mock(SaveImageStoragePort.class);
        passwordEncoder = mock(PasswordEncoder.class);
    }

    @Test
    void givenInitialSetupIsTrue_whenChangeNickname_thenThrowException() {
        // given
        Customer customer = Customer.builder()
                .nickname("nickname")
                .initialSetup(true)
                .build();

        // when & then
        assertThrows(CustomerException.class, () -> customer.changeNickname("newNick"));
    }

    @Test
    void givenEmptyNickname_whenChangeNickname_thenThrowException() {
        // given
        Customer customer = Customer.builder()
                .nickname("nickname")
                .initialSetup(false)
                .build();

        // when & then
        assertThrows(CustomerException.class, () -> customer.changeNickname(""));
    }

    @Test
    void givenValidNickname_whenChangeNickname_thenUpdateAndFlagInitialized() {
        // given
        Customer customer = Customer.builder()
                .initialSetup(false)
                .build();

        // when
        customer.changeNickname("newNick");

        // then
        assertEquals("newNick", customer.getNickname());
        assertTrue(customer.isInitialSetup());
    }
}