package com.breaditnow.common.security.resolver;

import com.breaditnow.common.exception.OwnerException;
import com.breaditnow.common.security.annotation.AuthOwner;
import com.breaditnow.owner.domain.model.Owner;
import com.breaditnow.owner.domain.port.out.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import static com.breaditnow.common.exception.OwnerErrorCode.AUTHENTICATION_REQUIRED;

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
	@Transactional
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
		AuthOwner authOwner = parameter.getParameterAnnotation(AuthOwner.class);

		String userIdHeader = webRequest.getHeader("X-Authorization-Id");
		if (userIdHeader == null) {
			if (authOwner.required()) {
				throw new OwnerException(AUTHENTICATION_REQUIRED);
			} else {
				return null;
			}
		}
		Long ownerId = Long.valueOf(userIdHeader);
		Owner owner = ownerRepository.findById(ownerId)
				.orElseGet(() -> {
					Owner newOwner = Owner.create(ownerId);
					return ownerRepository.save(newOwner);
				});

		if (Owner.class.isAssignableFrom(parameter.getParameterType())) {
			return owner;
		} else if (Long.class.isAssignableFrom(parameter.getParameterType())
			|| long.class.isAssignableFrom(parameter.getParameterType())) {
			return owner.getId();
		}
		return null;
	}
}
