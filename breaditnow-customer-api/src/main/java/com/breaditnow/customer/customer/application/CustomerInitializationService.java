package com.breaditnow.customer.customer.application;

import com.breaditnow.customer.customer.domain.port.LoadCustomerPort;
import com.breaditnow.customer.customer.domain.port.SaveCustomerPort;
import com.breaditnow.customer.customer.domain.port.SaveCustomerProductCategoryPort;
import com.breaditnow.customer.customer.application.request.CustomerInitRequest;
import com.breaditnow.customer.customer.domain.Customer;
import com.breaditnow.customer.product.application.port.LoadProductCategoryPort;
import com.breaditnow.customer.product.domain.ProductCategory;
import com.breaditnow.customer.common.exception.CustomerException;
import com.breaditnow.domain.global.exception.DomainException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static com.breaditnow.customer.common.exception.CustomerErrorCode.INVALID_BREAD_CATEGORY_IDS;
import static com.breaditnow.domain.global.exception.DomainErrorCode.DUPLICATE_NICKNAME;

@Service
@RequiredArgsConstructor
public class CustomerInitializationService {
    private final CustomerService customerService;
    private final LoadCustomerPort loadCustomerPort;
    private final SaveCustomerPort saveCustomerPort;
    private final LoadProductCategoryPort loadProductCategoryPort;
    private final SaveCustomerProductCategoryPort saveCustomerProductCategoryPort;

    @Transactional
    public void initCustomerInfo(Long customerId, CustomerInitRequest dto) {
        Customer customer = customerService.loadCustomer(customerId);
        if (loadCustomerPort.isExistNickName(dto.nickname())) {
            throw new DomainException(DUPLICATE_NICKNAME);
        }

        Set<ProductCategory> validCats = new HashSet<>(loadProductCategoryPort.findAllByIds(dto.breadCategoryIds()));
        if (validCats.size() != dto.breadCategoryIds().size()) {
            throw new CustomerException(INVALID_BREAD_CATEGORY_IDS);
        }

        for (ProductCategory category : validCats) {
            saveCustomerProductCategoryPort.preference(customer, category);
        }

        customer.changeNickname(dto.nickname());
        saveCustomerPort.save(customer);
    }
}
