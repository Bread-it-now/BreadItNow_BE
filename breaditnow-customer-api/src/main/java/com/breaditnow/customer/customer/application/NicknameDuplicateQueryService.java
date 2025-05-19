package com.breaditnow.customer.customer.application;

import com.breaditnow.customer.customer.application.request.NicknameDuplicateCheckRequest;
import com.breaditnow.customer.customer.application.response.NicknameDuplicateResponse;
import com.breaditnow.customer.customer.domain.port.CustomerPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NicknameDuplicateQueryService {
    private final CustomerPort customerPort;

    public NicknameDuplicateResponse isDuplicate(NicknameDuplicateCheckRequest dto) {
        Boolean existNickName = customerPort.isExistNickName(dto.nickname());
        return new NicknameDuplicateResponse(existNickName);
    }
}
