package com.breaditnow.auth.global.security.oauth2.service;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.breaditnow.auth.domain.auth.service.CustomerRegistrationService;
import com.breaditnow.auth.global.security.AccountContext;
import com.breaditnow.auth.global.security.oauth2.model.OAuth2UserInfo;
import com.breaditnow.domain.domain.customer.entity.Customer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

	private final CustomerRegistrationService customerRegistrationService;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User oAuth2User = super.loadUser(userRequest);
		String registrationId = userRequest.getClientRegistration().getRegistrationId();
		OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfo.of(registrationId, oAuth2User.getAttributes());

		Customer customer = customerRegistrationService.registerCustomer(oAuth2UserInfo);

		return AccountContext.ofOAuth2(customer.getId(), customer.getOauth2Id(), oAuth2User.getAttributes());
	}
}
