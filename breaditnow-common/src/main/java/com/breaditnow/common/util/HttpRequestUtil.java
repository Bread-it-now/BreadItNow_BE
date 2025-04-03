package com.breaditnow.common.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.util.StringUtils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * HttpServletRequest 에서 들어온 이용자 정보를 파싱하여 전달하기 위한 Util
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HttpRequestUtil {

	public static String getRequestUri(HttpServletRequest request) {
		return request.getRequestURI();
	}

	public static String getRequestFullUrl(HttpServletRequest request) {
		StringBuffer requestURL = request.getRequestURL();
		String queryString = request.getQueryString();
		return queryString == null ? requestURL.toString() : requestURL.append('?').append(queryString).toString();
	}

	public static Map<String, String> getParamMap(HttpServletRequest request) {
		Map<String, String> paramMap = new HashMap<>();
		request.getParameterNames().asIterator()
			.forEachRemaining(name -> paramMap.put(name, request.getParameter(name)));
		return paramMap;
	}

	public static Map<String, String> getHeaderMap(HttpServletRequest request) {
		Map<String, String> headerMap = new HashMap<>();
		request.getHeaderNames().asIterator()
			.forEachRemaining(name -> {
				if (!name.equals("user-agent")) {
					headerMap.put(name, request.getHeader(name));
				}
			});
		return headerMap.isEmpty() ? Map.of("No Headers", "No Header Information") : headerMap;
	}

	public static String getAgentDetail(HttpServletRequest request) {
		return request.getHeader("user-agent");
	}

	public static String getClientIP(HttpServletRequest httpReq) {
		String clientIp = httpReq.getHeader("X-Forwarded-For");
		if (!StringUtils.hasText(clientIp) || "unknown".equalsIgnoreCase(clientIp)) {
			// Proxy 서버인 경우
			clientIp = httpReq.getHeader("Proxy-Client-IP");
		}
		if (!StringUtils.hasText(clientIp) || "unknown".equalsIgnoreCase(clientIp)) {
			// WebLogic 서버인 경우
			clientIp = httpReq.getHeader("WL-Proxy-Client-IP");
		}
		if (!StringUtils.hasText(clientIp) || "unknown".equalsIgnoreCase(clientIp)) {
			clientIp = httpReq.getHeader("HTTP_CLIENT_IP");
		}
		if (!StringUtils.hasText(clientIp) || "unknown".equalsIgnoreCase(clientIp)) {
			clientIp = httpReq.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (!StringUtils.hasText(clientIp) || "unknown".equalsIgnoreCase(clientIp)) {
			clientIp = httpReq.getRemoteAddr();
		}
		String[] clientIpList = clientIp.split(",");
		return clientIpList[0];
	}

}
