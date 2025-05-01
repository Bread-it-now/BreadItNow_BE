package com.breaditnow.customer.domain.fcm.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.breaditnow.customer.domain.fcm.controller.req.FcmSendRequest;
import com.breaditnow.domain.domain.customer.entity.Customer;
import com.breaditnow.domain.domain.customer.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FcmService {
	private final CustomerRepository customerRepository;

	@Transactional
	public void updateToken(Long customerId, FcmSendRequest fcmSendRequest) {
		Customer customer = customerRepository.getById(customerId);
		customer.changeFcmToken(fcmSendRequest.fcmToken());
	}
}
