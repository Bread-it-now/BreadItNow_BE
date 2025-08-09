package com.breaditnow.common.security.resolver;

import com.breaditnow.common.exception.CustomerException;
import com.breaditnow.common.security.annotation.AuthCustomer;
import com.breaditnow.customer.domain.model.Customer;
import com.breaditnow.customer.domain.port.out.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Optional;

import static com.breaditnow.common.exception.CustomerErrorCode.AUTHENTICATION_REQUIRED;

@Component
@RequiredArgsConstructor
public class CustomerArgumentResolver implements HandlerMethodArgumentResolver {
	private final CustomerRepository customerRepository;

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		boolean hasAnnotation = parameter.hasParameterAnnotation(AuthCustomer.class);
		boolean isCustomerType = Customer.class.isAssignableFrom(parameter.getParameterType());
		boolean isLongType = Long.class.isAssignableFrom(parameter.getParameterType())
			|| long.class.isAssignableFrom(parameter.getParameterType());
		return hasAnnotation && (isCustomerType || isLongType);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
		AuthCustomer authCustomer = parameter.getParameterAnnotation(AuthCustomer.class);

		String userIdHeader = webRequest.getHeader("X-Authorization-Id");
		if (userIdHeader == null) {
			if (authCustomer.required()) {
				throw new CustomerException(AUTHENTICATION_REQUIRED);
			} else {
				return null;
			}
		}

		Long accountId = Long.valueOf(userIdHeader);
		Optional<Customer> optionalCustomer = customerRepository.findById(accountId);

		if (optionalCustomer.isPresent()) {
			Customer customer = optionalCustomer.get();
			if (Customer.class.isAssignableFrom(parameter.getParameterType())) {
				return customer;
			} else {
				return customer.getId();
			}
		}

		return accountId;
	}
}
