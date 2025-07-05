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
@RequestMapping("/api/v1/my/notification")
@RequiredArgsConstructor
public class MyNotificationController {
    private final NotificationReadUseCase readNotificationUseCase;
    private final NotificationDeleteUseCase notificationDeleteUseCase;
    private final NotificationQueryUseCase notificationQueryUseCase;

    @GetMapping
    public ApiSuccessResponse<NotificationPageResponse> getMyNotifications(
            @AuthUser AuthenticatedUser user,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(name = "category", required = false) String categoryStr
    ) {
        NotificationCategory category = NotificationCategory.from(categoryStr);
        return ApiSuccessResponse.of(notificationQueryUseCase.getNotifications(user, category, page, size));
    }

    @PostMapping("/{notificationId}/read")
    public ApiSuccessResponse<Void> readNotification(
            @AuthUser AuthenticatedUser user,
            @PathVariable Long notificationId
    ) {
        readNotificationUseCase.markNotificationAsRead(user, notificationId);
        return ApiSuccessResponse.of(null);
    }

    @DeleteMapping("/{notificationId}")
    public ApiSuccessResponse<Void> deleteNotification(
            @AuthUser AuthenticatedUser user,
            @PathVariable Long notificationId
    ) {
        notificationDeleteUseCase.notificationDelete(user, notificationId);
        return ApiSuccessResponse.of(null);
    }
}
