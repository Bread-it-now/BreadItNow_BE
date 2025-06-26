package com.breaditnow.customer.customer.application;

import com.breaditnow.customer.customer.application.request.CustomerInfoUpdateRequest;
import com.breaditnow.customer.customer.application.response.CustomerInfoResponse;
import com.breaditnow.customer.customer.domain.Customer;
import com.breaditnow.customer.customer.application.port.out.SaveImageStoragePort;
import com.breaditnow.customer.customer.application.port.out.CustomerRepositoryPort;
import com.breaditnow.domain.global.exception.DomainException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import static com.breaditnow.domain.global.exception.DomainErrorCode.DUPLICATE_NICKNAME;

@Service
@RequiredArgsConstructor
public class CustomerProfileService {
    private final CustomerService customerService;
    private final CustomerRepositoryPort customerRepositoryPort;
    private final SaveImageStoragePort storagePort;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public CustomerInfoResponse updateCustomerInfo(Long customerId, CustomerInfoUpdateRequest dto, MultipartFile profileImage) {
        if (customerRepositoryPort.isExistNickName(dto.nickname())) {
            throw new DomainException(DUPLICATE_NICKNAME);
        }

        Customer customer = customerService.loadCustomer(customerId);

        customer.updateInfo(dto.nickname(), dto.phone(), passwordEncoder.encode(dto.newPassword()), profileImage, storagePort);
        customer = customerRepositoryPort.save(customer);

        return CustomerInfoResponse.of(customer);
    }
}
