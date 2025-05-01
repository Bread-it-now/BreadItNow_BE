package com.breaditnow.gateway.jwt;

public record TokenUser(
	String userId,
	String role
) {
}
