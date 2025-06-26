package com.breaditnow.reservation.infrastructure.adapter.out.client;

import com.breaditnow.reservation.application.port.out.CustomerProfileProviderPort;
import com.breaditnow.reservation.domain.CustomerProfile;
import com.breaditnow.reservation.infrastructure.adapter.out.client.dto.InternalApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomerProfileAdapter implements CustomerProfileProviderPort {

    private final WebClient customerApiClient;

    @Override
    public Map<Long, CustomerProfile> getCustomerProfileMap(List<Long> customerIds) {
        if (customerIds == null || customerIds.isEmpty()) {
            return Collections.emptyMap();
        }

        var responseType = new ParameterizedTypeReference<InternalApiResponse<List<CustomerProfile>>>() {};
        try {
            InternalApiResponse<List<CustomerProfile>> apiResponse = customerApiClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/internal/v1/customer/profile")
                            .queryParam("ids", customerIds)
                            .build()
                    )
                    .retrieve()
                    .bodyToMono(responseType)
                    .block();

            if (apiResponse == null || !"SUCCESS".equalsIgnoreCase(apiResponse.status()) || apiResponse.data() == null) {
                log.warn("Received non-success or empty response from customer-api for ids: {}", customerIds);
                return Collections.emptyMap();
            }

            List<CustomerProfile> customerProfiles = apiResponse.data();

            return customerProfiles.stream()
                    .collect(Collectors.toMap(CustomerProfile::id, profile -> profile));
        } catch (Exception e) {
            log.error("Failed to call customer-api for ids: {}. Error: {}", customerIds, e.getMessage());
            return Collections.emptyMap();
        }
    }
}