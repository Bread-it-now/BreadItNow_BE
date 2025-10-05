package com.breaditnow.owner.application;

import com.breaditnow.common.exception.OwnerErrorCode;
import com.breaditnow.common.exception.OwnerException;
import com.breaditnow.owner.domain.model.Owner;
import com.breaditnow.owner.domain.port.in.OwnerUseCase;
import com.breaditnow.owner.domain.port.out.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.breaditnow.common.exception.OwnerErrorCode.AUTHENTICATION_REQUIRED;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OwnerService implements OwnerUseCase{
    private final OwnerRepository ownerRepository;

    @Override
    @Transactional
    public void updateFcmToken(Long ownerId, String fcmToken) {
        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new OwnerException(AUTHENTICATION_REQUIRED));

        owner.changeFcmToken(fcmToken);
        ownerRepository.save(owner);
    }

    @Override
    public Optional<Owner> findOwnerById(Long ownerId) {
        return ownerRepository.findById(ownerId);
    }

    @Override
    public boolean isOwnerInitialized(Long ownerId) {
        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new OwnerException(AUTHENTICATION_REQUIRED));

        return owner.isInitialized();
    }

    @Override
    @Transactional
    public void createOwnerInit(Long ownerId, String nickname) {
        if (ownerRepository.existsByNickname(nickname)) {
            throw new OwnerException(OwnerErrorCode.DUPLICATE_NICKNAME);
        }
        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new OwnerException(AUTHENTICATION_REQUIRED));
        owner.initialize(nickname);
        ownerRepository.save(owner);
    }
}