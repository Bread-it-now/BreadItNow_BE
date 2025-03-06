package com.breaditnow.auth.global.security;

import static com.breaditnow.auth.global.exception.AuthErrorCode.*;
import static com.breaditnow.auth.global.security.Role.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.BadCredentialsException;

import com.breaditnow.auth.global.exception.AuthException;

class RoleTest {
	@Test
	@DisplayName("유효한 문자열 입력 시 올바른 Role로 변환된다.")
	public void testFrom_Valid() {
		assertThat(Role.from("customer")).isEqualTo(CUSTOMER);
		assertThat(Role.from("CUSTOMER")).isEqualTo(CUSTOMER);
		assertThat(Role.from(" owner ")).isEqualTo(OWNER);
		assertThat(Role.from("OWNER")).isEqualTo(OWNER);
	}

	@Test
	@DisplayName("빈 문자열, 공백, null 입력 시 AuthException 발생한다.")
	public void testFrom_Invalid_NullOrEmpty() {
		assertThatThrownBy(() -> Role.from(""))
			.isInstanceOf(AuthException.class)
			.hasMessageContaining(ROLE_INVALID.getMessage());
		assertThatThrownBy(() -> Role.from("   "))
			.isInstanceOf(AuthException.class)
			.hasMessageContaining(ROLE_INVALID.getMessage());
		assertThatThrownBy(() -> Role.from(null))
			.isInstanceOf(AuthException.class)
			.hasMessageContaining(ROLE_INVALID.getMessage());
	}

	@Test
	@DisplayName("정의되지 않은 역할 문자열 입력 시 AuthException 발생한다.")
	public void testFrom_Invalid_WrongValue() {
		assertThatThrownBy(() -> Role.from("invalidRole"))
			.isInstanceOf(AuthException.class)
			.hasMessageContaining(ROLE_INVALID.getMessage());
	}

	@Test
	@DisplayName("유효한 Role 객체 입력 시 'ROLE_' 접두어와 함께 문자열 반환한다.")
	public void testToAuthority_Valid() {
		assertThat(toAuthority(CUSTOMER)).isEqualTo("ROLE_CUSTOMER");
		assertThat(toAuthority(OWNER)).isEqualTo("ROLE_OWNER");
	}

	@Test
	@DisplayName("null Role 입력 시 BadCredentialsException 발생한다.")
	public void testToAuthority_Null() {
		assertThatThrownBy(() -> toAuthority(null))
			.isInstanceOf(BadCredentialsException.class)
			.hasMessageContaining(ROLE_INVALID.name());
	}
}
