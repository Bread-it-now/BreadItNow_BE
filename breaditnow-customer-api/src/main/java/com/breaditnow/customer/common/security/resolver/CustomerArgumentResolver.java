package com.breaditnow.customer.common.security.resolver;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.breaditnow.customer.common.exception.CustomerException;
import com.breaditnow.customer.common.security.annotation.AuthCustomer;
import com.breaditnow.domain.domain.customer.entity.Customer;
import com.breaditnow.domain.domain.customer.repository.CustomerRepository;
import com.breaditnow.domain.domain.owner.entity.Owner;

import lombok.RequiredArgsConstructor;

import static com.breaditnow.customer.common.exception.CustomerErrorCode.AUTHENTICATION_REQUIRED;

@Component
@RequiredArgsConstructor
public class CustomerArgumentResolver implements HandlerMethodArgumentResolver {

	private final CustomerRepository customerRepository;

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		boolean hasAnnotation = parameter.hasParameterAnnotation(AuthCustomer.class);
		boolean isOwnerType = Owner.class.isAssignableFrom(parameter.getParameterType());
		boolean isLongType = Long.class.isAssignableFrom(parameter.getParameterType())
			|| long.class.isAssignableFrom(parameter.getParameterType());
		return hasAnnotation && (isOwnerType || isLongType);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		AuthCustomer authCustomer = parameter.getParameterAnnotation(AuthCustomer.class);

		String userIdHeader = webRequest.getHeader("X-Authorization-Id");
		if (userIdHeader == null) {
			if (authCustomer.required()) { // 필수인 경우, 예외 발생
				throw new CustomerException(AUTHENTICATION_REQUIRED);
			} else {
				return null;
			}
		}

		Customer customer = customerRepository.getById(Long.valueOf(userIdHeader));

		if (Customer.class.isAssignableFrom(parameter.getParameterType())) {
			return customer;
		} else if (Long.class.isAssignableFrom(parameter.getParameterType())
			|| long.class.isAssignableFrom(parameter.getParameterType())) {
			return customer.getId();
		}
		return null;
	}
}
