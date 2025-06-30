package com.breaditnow.reservation.application.provider;

import com.breaditnow.common.exception.ReservationException;
import com.breaditnow.reservation.application.dto.internal.ProductInfo;
import com.breaditnow.reservation.domain.port.out.OwnerApiPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.breaditnow.common.exception.ReservationErrorCode.PRODUCT_NOT_FOUND;

@Component
@RequiredArgsConstructor
public class ProductProvider {
    private final OwnerApiPort ownerApiPort;

    public List<ProductInfo> provideAllByIds(List<Long> productIds, Long bakeryId) {
        List<ProductInfo> productInfos = ownerApiPort.findProductsByIds(productIds, bakeryId);

        if (productInfos.size() != productIds.size()) {
            throw new ReservationException(PRODUCT_NOT_FOUND);
        }

        return productInfos;
    }
}
