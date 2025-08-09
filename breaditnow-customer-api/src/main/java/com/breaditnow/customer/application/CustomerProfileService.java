package com.breaditnow.customer.application;

import com.breaditnow.common.exception.CustomerException;
import com.breaditnow.customer.application.dto.request.CustomerInfoUpdateRequest;
import com.breaditnow.customer.application.dto.response.CustomerInfoResponse;
import com.breaditnow.customer.domain.model.Customer;
import com.breaditnow.customer.domain.port.out.CustomerRepository;
import com.breaditnow.customer.domain.port.out.PublishEventPort;
import com.breaditnow.customer.domain.port.out.SaveImageStoragePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import static com.breaditnow.common.exception.CustomerErrorCode.DUPLICATE_NICKNAME;


@Service
@RequiredArgsConstructor
public class CustomerProfileService {
    private final CustomerService customerService;
    private final CustomerRepository customerRepository;
    private final SaveImageStoragePort storagePort;
    private final PublishEventPort publishEventPort;

    @Transactional
    public CustomerInfoResponse updateCustomerInfo(Long customerId, CustomerInfoUpdateRequest dto, MultipartFile profileImage) {
        if (customerRepository.isExistNickName(dto.nickname())) {
            throw new CustomerException(DUPLICATE_NICKNAME);
        }

        Customer customer = customerService.loadCustomer(customerId);
        customer.updateInfo(dto.nickname(), dto.phone(), dto.newPassword(), profileImage, storagePort, publishEventPort);
        Customer savedCustomer = customerRepository.save(customer);

        return CustomerInfoResponse.of(savedCustomer);
    }
}
