package com.breaditnow.reservation.application;

import com.breaditnow.reservation.application.dto.event.BakeryCreatedEvent;
import com.breaditnow.reservation.application.dto.event.BakeryOperatingStatusChangedEvent;
import com.breaditnow.reservation.application.port.in.BakeryInfoSynchronizationUseCase;
import com.breaditnow.reservation.application.port.out.BakeryOperationalInfoRepositoryPort;
import com.breaditnow.reservation.domain.BakeryOperationalInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BakeryInfoSynchronizationService implements BakeryInfoSynchronizationUseCase {
    private final BakeryOperationalInfoRepositoryPort repositoryPort;

    @Override
    @Transactional
    public void createBakeryRecord(BakeryCreatedEvent event) {
        if (repositoryPort.findByBakeryId(event.bakeryId()).isPresent()) {
            log.warn("Bakery record for bakeryId {} already exists. A 'Created' event may have been duplicated.", event.bakeryId());
            return;
        }

        BakeryOperationalInfo newInfo = BakeryOperationalInfo.builder()
                .bakeryId(event.bakeryId())
                .operatingStatus(event.operatingStatus())
                .deleted(false)
                .build();

        repositoryPort.save(newInfo);
    }

    @Override
    @Transactional
    public void synchronizeStatus(BakeryOperatingStatusChangedEvent event) {
        Optional<BakeryOperationalInfo> optionalInfo = repositoryPort.findByBakeryId(event.bakeryId());

        if (optionalInfo.isPresent()) {
            BakeryOperationalInfo existingInfo = optionalInfo.get();
            existingInfo.updateOperatingStatus(event.operatingStatus());
            repositoryPort.save(existingInfo);
        } else {
            log.warn("BakeryOperationalInfo for bakeryId {} not found. A 'StatusChanged' event may have arrived before a 'Created' event. Creating a new record to self-heal.", event.bakeryId());
            BakeryOperationalInfo newInfo = BakeryOperationalInfo.builder()
                    .bakeryId(event.bakeryId())
                    .operatingStatus(event.operatingStatus())
                    .deleted(false)
                    .build();

            repositoryPort.save(newInfo);
        }
    }
}
