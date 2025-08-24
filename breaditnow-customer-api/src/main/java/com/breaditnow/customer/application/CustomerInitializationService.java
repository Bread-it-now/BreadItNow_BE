package com.breaditnow.customer.application;

import com.breaditnow.common.exception.CustomerException;
import com.breaditnow.customer.application.dto.request.CustomerInitRequest;
import com.breaditnow.customer.domain.model.Customer;
import com.breaditnow.customer.domain.port.out.CustomerRepository;
import com.breaditnow.customer.domain.port.out.CustomerProductCategoryRepository;
import com.breaditnow.product.domain.ProductCategory;
import com.breaditnow.product.domain.port.ProductCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static com.breaditnow.common.exception.CustomerErrorCode.DUPLICATE_NICKNAME;
import static com.breaditnow.common.exception.CustomerErrorCode.INVALID_BREAD_CATEGORY_IDS;

@Service
@RequiredArgsConstructor
public class CustomerInitializationService {
    private final CustomerRepository customerRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final CustomerProductCategoryRepository customerProductCategoryRepository;

    @Transactional
    public void initCustomerInfo(Long customerId, CustomerInitRequest dto) {
        if (customerRepository.isExistNickName(dto.nickname())) {
            throw new CustomerException(DUPLICATE_NICKNAME);
        }

        Customer customer = Customer.builder()
                .id(customerId)
                .nickname(dto.nickname())
                .build();

        Set<ProductCategory> validCats = new HashSet<>(productCategoryRepository.findAllByIds(dto.breadCategoryIds()));
        if (validCats.size() != dto.breadCategoryIds().size()) {
            throw new CustomerException(INVALID_BREAD_CATEGORY_IDS);
        }

        for (ProductCategory category : validCats) {
            customerProductCategoryRepository.preference(customer, category);
        }

        customer.changeNickname(dto.nickname());
        customerRepository.save(customer);
    }

    public Boolean isFirstLogin(Long customerId) {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        return customerOptional.isEmpty();
    }
}
