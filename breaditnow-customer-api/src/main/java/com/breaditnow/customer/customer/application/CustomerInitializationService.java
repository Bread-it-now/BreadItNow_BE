package com.breaditnow.customer.customer.application;

import com.breaditnow.customer.customer.domain.port.out.CustomerRepository;
import com.breaditnow.customer.customer.domain.port.out.SaveCustomerProductCategoryPort;
import com.breaditnow.customer.customer.application.request.CustomerInitRequest;
import com.breaditnow.customer.customer.domain.model.Customer;
import com.breaditnow.customer.product.domain.port.LoadProductCategoryPort;
import com.breaditnow.customer.product.domain.ProductCategory;
import com.breaditnow.customer.common.exception.CustomerException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

import static com.breaditnow.customer.common.exception.CustomerErrorCode.DUPLICATE_NICKNAME;
import static com.breaditnow.customer.common.exception.CustomerErrorCode.INVALID_BREAD_CATEGORY_IDS;

@Service
@RequiredArgsConstructor
public class CustomerInitializationService {
    private final CustomerService customerService;
    private final CustomerRepository customerRepository;
    private final LoadProductCategoryPort loadProductCategoryPort;
    private final SaveCustomerProductCategoryPort saveCustomerProductCategoryPort;

    @Transactional
    public void initCustomerInfo(Long customerId, CustomerInitRequest dto) {
        Customer customer = customerService.loadCustomer(customerId);
        if (customerRepository.isExistNickName(dto.nickname())) {
            throw new CustomerException(DUPLICATE_NICKNAME);
        }

        Set<ProductCategory> validCats = new HashSet<>(loadProductCategoryPort.findAllByIds(dto.breadCategoryIds()));
        if (validCats.size() != dto.breadCategoryIds().size()) {
            throw new CustomerException(INVALID_BREAD_CATEGORY_IDS);
        }

        for (ProductCategory category : validCats) {
            saveCustomerProductCategoryPort.preference(customer, category);
        }

        customer.changeNickname(dto.nickname());
        customerRepository.save(customer);
    }
}
