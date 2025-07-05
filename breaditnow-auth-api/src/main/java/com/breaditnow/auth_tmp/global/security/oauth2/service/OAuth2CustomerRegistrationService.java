//package com.breaditnow.auth_tmp.global.security.oauth2.service;
//
//import java.util.Optional;
//
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.breaditnow.auth_tmp.global.security.oauth2.model.OAuth2UserInfo;
//import com.breaditnow.domain.domain.customer.entity.Customer;
//import com.breaditnow.domain.domain.customer.repository.CustomerRepository;
//
//import lombok.RequiredArgsConstructor;
//
//@Service
//@RequiredArgsConstructor
//public class OAuth2CustomerRegistrationService {
//	private final CustomerRepository customerRepository;
//
//	@Transactional
//	public Customer registerCustomer(OAuth2UserInfo oAuth2UserInfo) {
//		Optional<Customer> existingCustomer = customerRepository.findByOauth2Id(oAuth2UserInfo.oauth2Id());
//		return existingCustomer.map(this::updateCustomer)
//			.orElseGet(() -> createCustomer(oAuth2UserInfo));
//	}
//
//	private Customer updateCustomer(Customer customer) {
//		customer.updateLastLoginAt();
//		return customer;
//	}
//
//	private Customer createCustomer(OAuth2UserInfo oAuth2UserInfo) {
//		Customer newCustomer = oAuth2UserInfo.toEntity();
//		return customerRepository.save(newCustomer);
//	}
//
//}
