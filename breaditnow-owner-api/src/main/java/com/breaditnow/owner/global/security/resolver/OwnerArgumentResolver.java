package com.breaditnow.owner.global.security.resolver;

import static com.breaditnow.owner.global.exception.OwnerErrorCode.*;

import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.breaditnow.common.security.AccountContext;
import com.breaditnow.domain.domain.owner.entity.Owner;
import com.breaditnow.domain.domain.owner.repository.OwnerRepository;
import com.breaditnow.owner.global.exception.OwnerException;
import com.breaditnow.owner.global.security.AuthOwner;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OwnerArgumentResolver implements HandlerMethodArgumentResolver {

	private final OwnerRepository ownerRepository;

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		boolean hasAnnotation = parameter.hasParameterAnnotation(AuthOwner.class);
		boolean isOwnerType = Owner.class.isAssignableFrom(parameter.getParameterType());
		boolean isLongType = Long.class.isAssignableFrom(parameter.getParameterType())
			|| long.class.isAssignableFrom(parameter.getParameterType());
		return hasAnnotation && (isOwnerType || isLongType);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		AuthOwner authOwner = parameter.getParameterAnnotation(AuthOwner.class);

		if (authentication == null || !(authentication.getPrincipal() instanceof AccountContext)) {
			if (authOwner.required()) { // 필수인 경우, 예외 발생
				throw new OwnerException(AUTHENTICATION_REQUIRED);
			} else {
				return null;
			}
		}

		AccountContext accountContext = (AccountContext)authentication.getPrincipal();
		Owner owner = ownerRepository.getById(accountContext.getUserId());

		if (Owner.class.isAssignableFrom(parameter.getParameterType())) {
			return owner;
		} else if (Long.class.isAssignableFrom(parameter.getParameterType())
			|| long.class.isAssignableFrom(parameter.getParameterType())) {
			return owner.getId();
		}
		return null;
	}
}
