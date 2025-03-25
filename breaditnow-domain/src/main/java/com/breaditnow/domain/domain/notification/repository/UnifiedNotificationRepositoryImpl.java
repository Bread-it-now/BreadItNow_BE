package com.breaditnow.domain.domain.notification.repository;

import static com.breaditnow.domain.domain.notification.entity.QCustomerNotification.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import com.breaditnow.domain.domain.notification.entity.CustomerNotification;
import com.breaditnow.domain.domain.notification.enumerate.NotificationType;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;

@Repository
public class UnifiedNotificationRepositoryImpl extends QuerydslRepositorySupport
	implements UnifiedNotificationRepositoryCustom {

	public UnifiedNotificationRepositoryImpl() {
		super(CustomerNotification.class);
	}

	@Override
	public Page<CustomerNotification> find(Long customerId, Pageable pageable,
		List<NotificationType> notificationTypes) {

		JPQLQuery<CustomerNotification> query = from(customerNotification)
			.select(customerNotification)
			.where(
				customerNotification.customer.id.eq(customerId)
					.and(customerNotification.isActive.eq(true))
			);

		if (notificationTypes != null && !notificationTypes.isEmpty()) {
			BooleanBuilder typePredicate = new BooleanBuilder();
			for (NotificationType type : notificationTypes) {
				typePredicate.or(customerNotification.notificationType.eq(type.name()));
			}
			if (typePredicate.hasValue()) {
				query.where(typePredicate);
			}
		}

		JPQLQuery<CustomerNotification> paginatedQuery = getQuerydsl().applyPagination(pageable, query);
		List<CustomerNotification> content = paginatedQuery.fetch();
		return new PageImpl<>(content, pageable, query.fetchCount());
	}
}
