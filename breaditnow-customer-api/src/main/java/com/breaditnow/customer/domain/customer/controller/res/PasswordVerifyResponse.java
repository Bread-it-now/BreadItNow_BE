package com.breaditnow.customer.domain.customer.controller.res;

public record PasswordVerifyResponse(
	Boolean verified
) {
	public static PasswordVerifyResponse of(boolean verified) {
		return new PasswordVerifyResponse(verified);
	}
}
