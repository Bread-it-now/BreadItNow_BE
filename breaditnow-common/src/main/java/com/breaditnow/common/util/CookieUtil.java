package com.breaditnow.common.util;

import java.util.Base64;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CookieUtil {
	public Optional<Cookie> getCookie(HttpServletRequest request, String cookieName) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null && cookies.length > 0) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(cookieName)) {
					return Optional.of(cookie);
				}
			}
		}
		return Optional.empty();
	}

	public void addCookie(HttpServletResponse response,
		String cookieName, String cookieValue, int maxAge) {
		ResponseCookie cookie = ResponseCookie.from(cookieName, cookieValue)
			.path("/")
			.sameSite("None")
			.httpOnly(false)
			.secure(true)
			.maxAge(maxAge)
			.build();
		response.addHeader("Set-Cookie", cookie.toString());
	}

	public void deleteCookie(HttpServletRequest request, HttpServletResponse response, String cookieName) {
		Optional<Cookie> optionalCookie = getCookie(request, cookieName);
		if (optionalCookie.isPresent()) {
			Cookie cookie = optionalCookie.get();
			cookie.setValue("");
			cookie.setPath("/");
			cookie.setMaxAge(0);
			response.addCookie(cookie);
		}
	}

	public <T> String serialize(T request) {
		return Base64.getUrlEncoder().encodeToString(SerializationUtils.serialize(request));
	}

	public <T> T deserialize(Cookie cookie, Class<T> clz) {
		if (isDeleted(cookie))
			return null;
		return clz.cast(SerializationUtils.deserialize(
			Base64.getUrlDecoder().decode(cookie.getValue()))
		);
	}

	private static boolean isDeleted(Cookie cookie) {
		return StringUtils.isBlank(cookie.getValue()) || Objects.isNull(cookie.getValue());
	}
}
