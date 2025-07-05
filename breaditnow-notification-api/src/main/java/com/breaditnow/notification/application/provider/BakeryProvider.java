package com.breaditnow.notification.application.provider;

import com.breaditnow.common.exception.NotificationException;
import com.breaditnow.notification.application.internal.BakeryInfo;
import com.breaditnow.notification.domain.port.out.OwnerApiPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.breaditnow.common.exception.NotificationErrorCode.BAKERY_NOT_FOUND;

@Component
@RequiredArgsConstructor
public class BakeryProvider {
    private final OwnerApiPort ownerApiPort;

    public BakeryInfo provide(Long bakeryId) {
        return ownerApiPort.findBakeryInfoById(bakeryId)
                .orElseThrow(() -> new NotificationException(BAKERY_NOT_FOUND));
    }
}
