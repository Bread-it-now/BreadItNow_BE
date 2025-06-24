package com.breaditnow.reservation.application;

import com.breaditnow.reservation.application.port.in.DeleteBakerySyncDataUseCase;
import com.breaditnow.reservation.application.port.out.BakeryOperationalInfoRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeleteBakerySyncDataService implements DeleteBakerySyncDataUseCase {
    private final BakeryOperationalInfoRepositoryPort repositoryPort;

    @Override
    @Transactional
    public void delete(Long bakeryId) {
        log.info("Marking operational info as deleted for bakeryId: {}", bakeryId);

        repositoryPort.findByBakeryId(bakeryId)
                .ifPresent(info -> {
                    info.delete();
                    repositoryPort.save(info);
                });
    }
}
