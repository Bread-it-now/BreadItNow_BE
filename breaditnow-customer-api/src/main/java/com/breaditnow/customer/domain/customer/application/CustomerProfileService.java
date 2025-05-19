package com.breaditnow.customer.domain.customer.application;

import com.breaditnow.customer.domain.customer.core.port.CustomerPort;
import com.breaditnow.customer.domain.customer.application.request.CustomerInfoUpdateRequest;
import com.breaditnow.customer.domain.customer.application.response.CustomerInfoResponse;
import com.breaditnow.customer.domain.customer.core.Customer;
import com.breaditnow.customer.domain.customer.core.port.ImageStoragePort;
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
    private final CustomerPort customerPort;
    private final ImageStoragePort storagePort;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public CustomerInfoResponse updateCustomerInfo(Long customerId, CustomerInfoUpdateRequest dto, MultipartFile profileImage) {
        if (customerPort.isExistNickName(dto.nickname())) {
            throw new DomainException(DUPLICATE_NICKNAME);
        }

        Customer customer = customerPort.findById(customerId);

        customer.updateInfo(dto.nickname(), dto.phone(), passwordEncoder.encode(dto.newPassword()), profileImage, storagePort);
        customer = customerPort.save(customer);

        return CustomerInfoResponse.of(customer);
    }
}
