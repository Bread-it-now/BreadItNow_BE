package com.breaditnow.reservation.application;

import com.breaditnow.reservation.application.dto.event.BakeryCreatedEvent;
import com.breaditnow.reservation.application.dto.event.BakeryDeletedEvent;
import com.breaditnow.reservation.application.dto.event.BakeryUpdatedEvent;
import com.breaditnow.reservation.application.port.in.BakeryInfoSynchronizationUseCase;
import com.breaditnow.reservation.application.port.out.BakeryOperationalInfoRepositoryPort;
import com.breaditnow.reservation.domain.BakeryInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BakeryInfoSynchronizationService implements BakeryInfoSynchronizationUseCase {
    private final BakeryOperationalInfoRepositoryPort repositoryPort;

    @Override
    public void createBakeryInfo(BakeryCreatedEvent event) {
        if (repositoryPort.findByBakeryId(event.bakeryId()).isPresent()) {
            log.warn("Bakery record for bakeryId {} already exists. A 'Created' event may have been duplicated.", event.bakeryId());
            return;
        }

        BakeryInfo newInfo = BakeryInfo.builder()
                .bakeryId(event.bakeryId())
                .operatingStatus(event.operatingStatus())
                .deleted(false)
                .build();

        repositoryPort.save(newInfo);
    }

    @Override
    public void updateBakeryInfo(BakeryUpdatedEvent event) {
        Optional<BakeryInfo> optionalInfo = repositoryPort.findByBakeryId(event.bakeryId());

        if (optionalInfo.isPresent()) {
            BakeryInfo existingInfo = optionalInfo.get();
            existingInfo.updateOperatingStatus(event.operatingStatus());
            repositoryPort.save(existingInfo);
        } else {
            log.warn("BakeryOperationalInfo for bakeryId {} not found. A 'StatusChanged' event may have arrived before a 'Created' event. Creating a new record to self-heal.", event.bakeryId());
            BakeryInfo newInfo = BakeryInfo.builder()
                    .bakeryId(event.bakeryId())
                    .operatingStatus(event.operatingStatus())
                    .deleted(false)
                    .build();

            repositoryPort.save(newInfo);
        }
    }

    @Override
    public void deleteBakeryInfo(BakeryDeletedEvent event) {
        log.info("Marking operational info as deleted for bakeryId: {}", event.bakeryId());

        repositoryPort.findByBakeryId(event.bakeryId())
                .ifPresent(info -> {
                    info.delete();
                    repositoryPort.save(info);
                });
    }
}
