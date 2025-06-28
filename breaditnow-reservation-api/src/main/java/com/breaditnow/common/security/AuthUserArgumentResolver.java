package com.breaditnow.common.security;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class AuthUserArgumentResolver implements HandlerMethodArgumentResolver {
    private static final String USER_ID_HEADER = "X-Authorization-Id";
    private static final String USER_ROLE_HEADER = "X-Authorization-Role";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthUser.class) &&
                parameter.getParameterType().equals(AuthenticatedUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory){

        String userIdHeader = webRequest.getHeader(USER_ID_HEADER);
        String roleHeader = webRequest.getHeader(USER_ROLE_HEADER);
        assert userIdHeader != null;

        Long userId = Long.parseLong(userIdHeader);
        return new AuthenticatedUser(userId, roleHeader);
    }
}
