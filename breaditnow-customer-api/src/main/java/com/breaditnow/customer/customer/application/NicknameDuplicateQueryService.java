package com.breaditnow.customer.customer.application;

import com.breaditnow.customer.customer.application.request.NicknameDuplicateCheckRequest;
import com.breaditnow.customer.customer.application.response.NicknameDuplicateResponse;
import com.breaditnow.customer.customer.domain.port.out.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NicknameDuplicateQueryService {
    private final CustomerRepository customerRepository;

    public NicknameDuplicateResponse isDuplicate(NicknameDuplicateCheckRequest dto) {
        Boolean existNickName = customerRepository.isExistNickName(dto.nickname());
        return new NicknameDuplicateResponse(existNickName);
    }
}
