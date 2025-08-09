package com.breaditnow.customer.application;

import com.breaditnow.customer.application.dto.request.NicknameDuplicateCheckRequest;
import com.breaditnow.customer.application.dto.response.NicknameDuplicateResponse;
import com.breaditnow.customer.domain.port.out.CustomerRepository;
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
