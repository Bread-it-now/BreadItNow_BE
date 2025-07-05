package com.breaditnow.notification.adapter.in.web;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.notification.adapter.in.web.resolver.AuthUser;
import com.breaditnow.notification.adapter.in.web.resolver.AuthenticatedUser;
import com.breaditnow.notification.application.dto.response.NotificationPageResponse;
import com.breaditnow.notification.domain.model.NotificationCategory;
import com.breaditnow.notification.domain.port.in.NotificationDeleteUseCase;
import com.breaditnow.notification.domain.port.in.NotificationQueryUseCase;
import com.breaditnow.notification.domain.port.in.NotificationReadUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/bakery/{bakeryId}/notification")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationQueryUseCase notificationQueryUseCase;
    private final NotificationReadUseCase notificationReadUseCase;
    private final NotificationDeleteUseCase notificationDeleteUseCase;

    @GetMapping
    public ApiSuccessResponse<NotificationPageResponse> getNotifications(
            @AuthUser AuthenticatedUser user,
            @PathVariable(name = "bakeryId") Long bakeryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(name = "category", required = false) String categoryStr
    ) {
        NotificationCategory category = NotificationCategory.from(categoryStr);
        return ApiSuccessResponse.of(notificationQueryUseCase.getNotifications(user, bakeryId, category, page, size));
    }

    @PostMapping("/{notificationId}/read")
    public ApiSuccessResponse<Void> markNotificationAsRead(
            @AuthUser AuthenticatedUser user,
            @PathVariable Long bakeryId,
            @PathVariable Long notificationId
    ) {
        notificationReadUseCase.markNotificationAsRead(user, bakeryId, notificationId);
        return ApiSuccessResponse.of();
    }

    @DeleteMapping("/{notificationId}")
    public ApiSuccessResponse<Void> deleteNotification(
            @AuthUser AuthenticatedUser user,
            @PathVariable Long bakeryId,
            @PathVariable Long notificationId
    ) {
        notificationDeleteUseCase.notificationDelete(user, bakeryId, notificationId);
        return ApiSuccessResponse.of();
    }
}
