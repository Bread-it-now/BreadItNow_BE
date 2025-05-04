package com.breaditnow.customer.domain.customer.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.breaditnow.customer.domain.customer.controller.req.CustomerInfoUpdateRequest;
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
import com.breaditnow.external.domain.s3.FileUploaderService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CustomerService {

	private final CustomerRepository customerRepository;
	private final RegionRepository regionRepository;
	private final CustomerRegionPreferenceRepository customerRegionPreferenceRepository;
	private final BreadCategoryRepository breadCategoryRepository;
	private final FileUploaderService uploaderService;
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

	@Transactional
	public CustomerInfoResponse updateCustomerInfo(Long customerId, CustomerInfoUpdateRequest request,
		MultipartFile profileImage) {
		Customer customer = customerRepository.getById(customerId);

		customer.updateNickname(request.nickname());
		customer.updatePhone(request.phone());

		if (profileImage != null && !profileImage.isEmpty()) {
			String profileImageUrl = uploadFile(profileImage, "image/customer/" + customerId + "/profile");
			customer.updateProfileImage(profileImageUrl);
		}

		if (request.newPassword() != null && !request.newPassword().isBlank()) {
			String encodedPassword = passwordEncoder.encode(request.newPassword());
			customer.changePassword(encodedPassword);
		}

		return CustomerInfoResponse.of(customer);
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

	private String uploadFile(MultipartFile file, String path) {
		if (file != null && !file.isEmpty()) {
			return uploaderService.upload(file, path);
		}
		return "";
	}

}
