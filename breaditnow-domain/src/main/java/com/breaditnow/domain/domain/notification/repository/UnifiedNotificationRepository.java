package com.breaditnow.domain.domain.notification.repository;

import static com.breaditnow.domain.domain.notification.entity.QCustomerNotification.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.breaditnow.domain.domain.notification.entity.CustomerNotification;
import com.breaditnow.domain.domain.notification.enumerate.NotificationType;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UnifiedNotificationRepository {
	private final JPAQueryFactory queryFactory;

	public Page<CustomerNotification> find(Long customerId, Pageable pageable,
		List<NotificationType> notificationTypes) {
		BooleanExpression predicate = customerNotification.customer.id.eq(customerId);

		if (notificationTypes.size() == 1) {
			NotificationType type = notificationTypes.get(0);
			predicate = predicate.and(customerNotification.notificationType.eq(type.name()));
		}

		// Querydsl JPAQuery 작성
		List<CustomerNotification> content = queryFactory
			.select(customerNotification)
			.from(customerNotification)
			.where(predicate)
			.orderBy(customerNotification.createdAt.desc())
			.fetch();

		// 총 건수
		Long totalCount = queryFactory
			.select(customerNotification.count())
			.from(customerNotification)
			.where(predicate)
			.orderBy(customerNotification.createdAt.desc())
			.fetchOne();

		return new PageImpl<>(content, pageable, totalCount == null ? 0 : totalCount);
	}
}
