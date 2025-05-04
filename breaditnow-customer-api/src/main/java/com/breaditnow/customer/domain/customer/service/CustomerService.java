package com.breaditnow.customer.domain.customer.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.breaditnow.customer.domain.customer.controller.req.CustomerInitRequest;
import com.breaditnow.customer.domain.customer.controller.req.PasswordVerifyRequest;
import com.breaditnow.customer.domain.customer.controller.req.RegionUpdateRequest;
import com.breaditnow.customer.domain.customer.controller.res.CustomerInfoResponse;
import com.breaditnow.customer.domain.customer.controller.res.NicknameDuplicateResponse;
import com.breaditnow.customer.domain.customer.controller.res.PasswordVerifyResponse;
import com.breaditnow.domain.domain.breadcategory.repository.BreadCategoryRepository;
import com.breaditnow.domain.domain.customer.entity.Customer;
import com.breaditnow.domain.domain.customer.entity.CustomerRegionPreference;
import com.breaditnow.domain.domain.customer.repository.CustomerRegionPreferenceRepository;
import com.breaditnow.domain.domain.customer.repository.CustomerRepository;
import com.breaditnow.domain.domain.region.entity.Region;
import com.breaditnow.domain.domain.region.entity.RegionPK;
import com.breaditnow.domain.domain.region.repository.RegionRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CustomerService {

	private final CustomerRepository customerRepository;
	private final RegionRepository regionRepository;
	private final CustomerRegionPreferenceRepository customerRegionPreferenceRepository;
	private final BreadCategoryRepository breadCategoryRepository;
	private final PasswordEncoder passwordEncoder;

	@Transactional
	public void initCustomerInfo(Long customerId, CustomerInitRequest request) {
		Customer customer = customerRepository.getById(customerId);

		customer.updateNickname(request.nickname());

		customer.updateBreadCategoryPreferences(
			request.breadCategoryIds().stream()
				.map(breadCategoryRepository::getById)
				.toList()
		);
	}

	public CustomerInfoResponse getCustomerInfo(Long customerId) {
		Customer customer = customerRepository.getById(customerId);

		return CustomerInfoResponse.of(customer);
	}

	public PasswordVerifyResponse verifyPassword(Long customerId, PasswordVerifyRequest request) {
		Customer customer = customerRepository.getById(customerId);
		boolean verified = passwordEncoder.matches(request.password(), customer.getPassword());
		return PasswordVerifyResponse.of(verified);
	}

	public NicknameDuplicateResponse checkDuplicateNickname(String nickname) {
		boolean duplicated = customerRepository.existsByNickname(nickname);
		return NicknameDuplicateResponse.of(duplicated);
	}

	@Transactional
	public void updateCustomerRegions(Long customerId, RegionUpdateRequest request) {
		Customer customer = customerRepository.getById(customerId);

		customerRegionPreferenceRepository.deleteAllByCustomerId(customerId);

		List<CustomerRegionPreference> newPreferences = request.gugunCodes().stream()
			.map(gugunCode -> {
				String fullCode = String.format("%02d%03d000",
					Integer.parseInt(request.sidoCode()),
					Integer.parseInt(gugunCode));
				Region region = regionRepository.getById(new RegionPK(fullCode));

				return CustomerRegionPreference.builder()
					.customer(customer)
					.region(region)
					.build();
			})
			.toList();

		customerRegionPreferenceRepository.saveAll(newPreferences);
	}
}
