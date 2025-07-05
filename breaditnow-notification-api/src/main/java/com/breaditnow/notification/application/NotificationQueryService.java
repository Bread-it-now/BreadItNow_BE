package com.breaditnow.notification.application;

import com.breaditnow.notification.adapter.in.web.resolver.AuthenticatedUser;
import com.breaditnow.notification.application.dto.response.NotificationPageResponse;
import com.breaditnow.notification.application.internal.BakeryInfo;
import com.breaditnow.notification.application.provider.BakeryProvider;
import com.breaditnow.notification.application.validator.BakeryValidator;
import com.breaditnow.notification.domain.model.NotificationCategory;
import com.breaditnow.notification.domain.port.in.NotificationQueryUseCase;
import com.breaditnow.notification.domain.port.out.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotificationQueryService implements NotificationQueryUseCase {
    private final BakeryProvider bakeryProvider;
    private final BakeryValidator bakeryValidator;
    private final NotificationRepository notificationRepository;

    @Override
    public NotificationPageResponse getNotifications(AuthenticatedUser user, Long bakeryId, NotificationCategory category, int page, int size) {
        BakeryInfo bakeryInfo = bakeryProvider.provide(bakeryId);
        bakeryValidator.validateOwner(bakeryInfo, user);
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return NotificationPageResponse.from(notificationRepository.getNotifications(bakeryId, category, pageable));
    }
}
