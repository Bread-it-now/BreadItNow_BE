package com.breaditnow.customer.customer.application;

import com.breaditnow.customer.customer.domain.port.CustomerPort;
import com.breaditnow.customer.customer.domain.port.CustomerProductCategoryPort;
import com.breaditnow.customer.customer.application.request.CustomerInitRequest;
import com.breaditnow.customer.customer.domain.Customer;
import com.breaditnow.customer.product.application.port.ProductCategoryPort;
import com.breaditnow.customer.product.domain.ProductCategory;
import com.breaditnow.customer.common.exception.CustomerException;
import com.breaditnow.domain.global.exception.DomainException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

import static com.breaditnow.customer.common.exception.CustomerErrorCode.INVALID_BREAD_CATEGORY_IDS;
import static com.breaditnow.domain.global.exception.DomainErrorCode.DUPLICATE_NICKNAME;

@Service
@RequiredArgsConstructor
public class CustomerInitializationService {
    private final CustomerPort customerPort;
    private final ProductCategoryPort productCategoryPort;
    private final CustomerProductCategoryPort customerProductCategoryPort;

    @Transactional
    public void initCustomerInfo(Long customerId, CustomerInitRequest dto) {
        Customer customer = customerPort.findById(customerId);
        if (customerPort.isExistNickName(dto.nickname())) {
            throw new DomainException(DUPLICATE_NICKNAME);
        }

        Set<ProductCategory> validCats = new HashSet<>(productCategoryPort.findAllByIds(dto.breadCategoryIds()));
        if (validCats.size() != dto.breadCategoryIds().size()) {
            throw new CustomerException(INVALID_BREAD_CATEGORY_IDS);
        }

        for (ProductCategory category : validCats) {
            customerProductCategoryPort.preference(customer, category);
        }

        customer.changeNickname(dto.nickname());
        customerPort.save(customer);
    }
}
