package com.breaditnow.customer.customer.application;

import com.breaditnow.customer.customer.application.request.NicknameDuplicateCheckRequest;
import com.breaditnow.customer.customer.application.response.NicknameDuplicateResponse;
import com.breaditnow.customer.customer.application.port.out.CustomerRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NicknameDuplicateQueryService {
    private final CustomerRepositoryPort customerRepositoryPort;

    public NicknameDuplicateResponse isDuplicate(NicknameDuplicateCheckRequest dto) {
        Boolean existNickName = customerRepositoryPort.isExistNickName(dto.nickname());
        return new NicknameDuplicateResponse(existNickName);
    }
}
