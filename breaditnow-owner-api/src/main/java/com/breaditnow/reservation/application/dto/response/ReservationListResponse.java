package com.breaditnow.reservation.application.dto.response;

import com.breaditnow.common.page.PageInfo;
import com.breaditnow.reservation.domain.CustomerProfile;
import com.breaditnow.reservation.infrastructure.adapter.out.persistence.jpa.ReservationViewEntity;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public record ReservationListResponse(
        List<ReservationSummary> reservations,
        PageInfo pageInfo
) {
    public record ReservationSummary(
            Long reservationId,
            LocalDateTime reservationDate,
            String reservationNumber,
            String status,
            String consumerNickname,
            Integer totalPrice,
            LocalDateTime pickupDeadline
    ) {
        public static ReservationSummary from(ReservationViewEntity entity, String nickname) {
            String reservationNum = null;
            LocalDateTime deadline = null;
            return new ReservationSummary(
                    entity.getReservationId(),
                    entity.getReservationTime(),
                    entity.getReservationNumber() != null ? entity.getReservationNumber() : reservationNum,
                    entity.getStatus(),
                    nickname,
                    entity.getTotalPrice(),
                    deadline
            );
        }
    }

    public static ReservationListResponse of(Page<ReservationViewEntity> page, Map<Long, CustomerProfile> customerProfileMap) {
        List<ReservationSummary> summaries = page.getContent().stream()
                .map(entity -> {
                    String nickname = customerProfileMap.getOrDefault(entity.getCustomerId(), new CustomerProfile(null, "알 수 없음")).nickname();
                    return ReservationSummary.from(entity, nickname);
                })
                .toList();

        PageInfo pageInfo = PageInfo.of(page.getTotalElements(),
                page.getTotalPages(),
                page.isLast(),
                page.getNumber());

        return new ReservationListResponse(summaries, pageInfo);
    }
}
