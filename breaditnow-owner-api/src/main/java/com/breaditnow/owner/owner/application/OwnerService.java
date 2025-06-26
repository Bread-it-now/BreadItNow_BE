package com.breaditnow.owner.owner.application;

import com.breaditnow.owner.owner.domain.model.Owner;
import com.breaditnow.owner.owner.domain.port.in.OwnerUseCase;
import com.breaditnow.owner.owner.domain.port.out.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OwnerService implements OwnerUseCase{
    private final OwnerRepository ownerRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void changePassword(Long ownerId, String newPassword) {
        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new IllegalArgumentException("Owner not found"));

        owner.changePassword(newPassword, passwordEncoder);
        ownerRepository.save(owner);
    }

    @Override
    @Transactional
    public void updateFcmToken(Long ownerId, String fcmToken) {
        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new IllegalArgumentException("Owner not found"));

        owner.changeFcmToken(fcmToken);
        ownerRepository.save(owner);
    }

    @Override
    public Owner findOwnerById(Long ownerId) {
        return ownerRepository.findById(ownerId)
                .orElseThrow(() -> new IllegalArgumentException("Owner not found"));
    }
}
