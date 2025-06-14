package com.breaditnow.customer.bakery.infrastructure;

import com.breaditnow.customer.bakery.application.request.HotBakerySearchCriteria;
import com.breaditnow.customer.bakery.domain.port.LoadBakeryPort;
import com.breaditnow.customer.bakery.domain.port.SaveBakeryPort;
import com.breaditnow.customer.bakery.infrastructure.jpa.JpaBakeryRepository;
import com.breaditnow.customer.bakery.infrastructure.jpa.QueryBakeryRepository;
import com.breaditnow.customer.bakery.presentation.response.BakeryDetailResponse;
import com.breaditnow.customer.bakery.presentation.response.BakeryResponse;
import com.breaditnow.customer.bakery.presentation.response.HotBakeryPageResponse;
import com.breaditnow.customer.bakery.presentation.response.HotBakeryResponse;
import com.breaditnow.customer.product.infrastructure.jpa.query.QueryProductRepository;
import com.breaditnow.customer.product.presentation.response.BreadProductResponse;
import com.breaditnow.customer.product.presentation.response.OtherProductResponse;
import com.breaditnow.domain.global.exception.DomainException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.breaditnow.domain.global.exception.DomainErrorCode.BAKERY_NOT_FOUND;

@Repository
@RequiredArgsConstructor
public class BakeryAdapter implements SaveBakeryPort, LoadBakeryPort {
    private final JpaBakeryRepository jpaBakeryRepository;
    private final QueryProductRepository queryProductRepository;
    private final QueryBakeryRepository queryBakeryRepository;

    @Override
    public void increaseFavoriteCount(Long bakeryId) {
        jpaBakeryRepository.increaseFavoriteCount(bakeryId);
    }

    @Override
    public void decreaseFavoriteCount(Long bakeryId) {
        jpaBakeryRepository.decreaseFavoriteCount(bakeryId);
    }

    public void validateIsExistBakery(Long bakeryId) {
        if(!jpaBakeryRepository.existsById(bakeryId)) {
            throw new DomainException(BAKERY_NOT_FOUND);
        }
    }

    public BakeryDetailResponse getBakeryDetail(Long customerId, Long bakeryId) {
        BakeryResponse bakeryResponse = queryBakeryRepository.getBakery(customerId, bakeryId);
        List<BreadProductResponse> breadProductResponses = queryProductRepository.getBreadProductsByBakeryId(bakeryId);
        List<OtherProductResponse> otherProductResponses = queryProductRepository.getOtherProductsByBakeryId(bakeryId);
        return BakeryDetailResponse.of(bakeryResponse, breadProductResponses, otherProductResponses);
    }

    public HotBakeryPageResponse getHotBakeries(Long customerId, HotBakerySearchCriteria searchCriteria) {
        Page<HotBakeryResponse> hotBakeryResponses = queryBakeryRepository.fetchHotBakeries(customerId, searchCriteria);
        return HotBakeryPageResponse.of(hotBakeryResponses);
    }
}
