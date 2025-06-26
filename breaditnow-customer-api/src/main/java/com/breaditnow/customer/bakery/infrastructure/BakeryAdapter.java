package com.breaditnow.customer.bakery.infrastructure;

import com.breaditnow.customer.bakery.application.request.HotBakerySearchCriteria;
import com.breaditnow.customer.bakery.domain.port.SaveBakeryPort;
import com.breaditnow.customer.bakery.infrastructure.jpa.JpaBakeryRepository;
import com.breaditnow.customer.bakery.presentation.response.BakeryDetailResponse;
import com.breaditnow.customer.bakery.presentation.response.HotBakeryPageResponse;
import com.breaditnow.customer.common.exception.CustomerException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.breaditnow.customer.common.exception.CustomerErrorCode.BAKERY_NOT_FOUND;


@Repository
@RequiredArgsConstructor
public class BakeryAdapter implements SaveBakeryPort {
    private final JpaBakeryRepository jpaBakeryRepository;
//    private final QueryProductRepository queryProductRepository;
//    private final QueryBakeryRepository queryBakeryRepository;

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
            throw new CustomerException(BAKERY_NOT_FOUND);
        }
    }

    public BakeryDetailResponse getBakeryDetail(Long customerId, Long bakeryId) {
//        BakeryResponse bakeryResponse = queryBakeryRepository.getBakery(customerId, bakeryId);
//        List<BreadProductResponse> breadProductResponses = queryProductRepository.getBreadProductsByBakeryId(bakeryId);
//        List<OtherProductResponse> otherProductResponses = queryProductRepository.getOtherProductsByBakeryId(bakeryId);
//        return BakeryDetailResponse.of(bakeryResponse, breadProductResponses, otherProductResponses);
        return null;
    }

    public HotBakeryPageResponse getHotBakeries(Long customerId, HotBakerySearchCriteria searchCriteria) {
//        Page<HotBakeryResponse> hotBakeryResponses = queryBakeryRepository.fetchHotBakeries(customerId, searchCriteria);
//        return HotBakeryPageResponse.of(hotBakeryResponses);
        return null;
    }
}
