package com.breaditnow.domain.domain.bakery.repository;

import static com.breaditnow.domain.domain.bakery.entity.QBakery.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.breaditnow.domain.domain.bakery.entity.Bakery;
import com.breaditnow.domain.domain.customer.entity.QCustomerRegionPreference;
import com.breaditnow.domain.domain.favorite.entity.QCustomerBakeryFavorite;
import com.breaditnow.domain.domain.reservation.entity.QReservation;
import com.breaditnow.domain.domain.reservation.entity.QReservationProduct;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.JPQLQuery;

public class BakeryRepositoryImpl extends QuerydslRepositorySupport implements BakeryRepositoryCustom {
	public BakeryRepositoryImpl() {
		super(Bakery.class);
	}

	@Override
	public Page<Bakery> searchHotBakeries(Long customerId, String sort, Pageable pageable) {
		JPQLQuery<Bakery> query = from(bakery)
			.select(bakery)
			.where(bakery.isActive.eq(true));

		// 관심지역
		QCustomerRegionPreference preference = QCustomerRegionPreference.customerRegionPreference;
		query.leftJoin(preference)
			.on(preference.customer.id.eq(customerId));

		query.where(preference.isNull().or(
			preference.region.id.sidoCode.eq(bakery.address.sidoCode)
				.and(preference.region.id.gugunCode.eq(bakery.address.gugunCode))
		));

		OrderSpecifier<?> orderSpecifier = null;
		// Pageable의 Sort 조건이 존재하면 첫 번째 정렬 조건을 확인
		if ("reservation".equalsIgnoreCase(sort)) {
			// 예약 정렬: Reservation과 ReservationProduct를 조인하여 Bakery별 예약상품 개수를 기준으로 내림차순 정렬
			QReservation reservation = QReservation.reservation;
			QReservationProduct reservationProduct = QReservationProduct.reservationProduct;
			query.leftJoin(reservation).on(reservation.bakery.eq(bakery));
			query.leftJoin(reservationProduct).on(reservationProduct.reservation.eq(reservation));
			// 그룹핑: Bakery별로 그룹화하여 예약상품 수 집계
			query.groupBy(bakery.id);
			orderSpecifier = reservationProduct.count().desc();
		} else if ("like".equalsIgnoreCase(sort)) {
			// 즐겨찾기 정렬: CustomerBakeryFavorite을 조인하여 Bakery별 즐겨찾기 수 기준 내림차순 정렬
			QCustomerBakeryFavorite favorite = QCustomerBakeryFavorite.customerBakeryFavorite;
			query.leftJoin(favorite).on(favorite.bakery.eq(bakery));
			query.groupBy(bakery.id);
			orderSpecifier = favorite.count().desc();
		}
		query.orderBy(orderSpecifier);

		long total = query.fetchCount();
		List<Bakery> results = getQuerydsl().applyPagination(pageable, query).fetch();

		return new PageImpl<>(results, pageable, total);
	}
}
