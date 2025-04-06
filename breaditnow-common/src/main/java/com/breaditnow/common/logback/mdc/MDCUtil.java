package com.breaditnow.common.logback.mdc;

import org.slf4j.MDC;
import org.slf4j.spi.MDCAdapter;

import com.breaditnow.common.util.JsonUtil;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MDCUtil {
	public static final String HEADER_MAP_MDC = "HTTP 헤더 정보";
	public static final String PARAMETER_MAP_MDC = "Parameter 정보";
	public static final String REQUEST_URI_MDC = "이용자 요청 URI 정보";
	public static final String REQUEST_FULL_URL_MDC = "이용자 요청 전체 URL 정보";
	public static final String USER_IP_MDC = "이용자 IP 정보";
	public static final String USER_AGENT_DETAIL_MDC = "이용자 환경 정보";
	private static final MDCAdapter mdc = MDC.getMDCAdapter();

	public static void set(String key, String value) {
		mdc.put(key, value);
	}

	public static void setJsonValue(String key, Object value) {
		if (value != null) {
			String json = JsonUtil.toJson(value);
			mdc.put(key, json);

		} else {
			mdc.put(key, "내용 없음");
		}
	}

	public static void clear() {
		MDC.clear();
	}
}
