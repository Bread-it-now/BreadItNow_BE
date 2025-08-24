package com.breaditnow.product.infrastructure.jpa.query;

import com.breaditnow.product.application.request.ProductFavoriteSearchCriteria;
import com.breaditnow.product.presentation.response.ProductFavoriteResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class QueryProductFavoriteRepository {
//    private final JPAQueryFactory queryFactory;
//    private static final QProductFavoriteEntity productFavoriteEntity = QProductFavoriteEntity.productFavoriteEntity;
//    private static final QProductEntity productEntity = QProductEntity.productEntity;
//    private static final QBakeryEntity bakeryEntity = QBakeryEntity.bakeryEntity;
//    private final DistanceExpressionProvider distanceExpressionProvider;

    public Page<ProductFavoriteResponse> fetchProductFavorites(Long customerId, ProductFavoriteSearchCriteria criteria) {
//        NumberExpression<Double> distanceExpression = buildDistanceExpression(criteria);
//        JPAQuery<ProductFavoriteResponse> query = createQuery(customerId, criteria, distanceExpression);
//
//        Pageable pageable = criteria.pagination().toPageable();
//        query.offset(pageable.getOffset()).limit(pageable.getPageSize());
//
//        JPAQuery<Long> countQuery = createCountQuery(customerId);
//
//        return PageableExecutionUtils.getPage(query.fetch(), pageable, countQuery::fetchOne);
        return null;
    }
}

//    private JPAQuery<ProductFavoriteResponse> createQuery(Long customerId, ProductFavoriteSearchCriteria criteria, NumberExpression<Double> distanceExpression) {
//        return queryFactory.select(
//                        Projections.fields(
//                                ProductFavoriteResponse.class,
//                                productEntity.id.as("productId"),
//                                productEntity.bakeryId.as("bakeryId"),
//                                productEntity.name.as("productName"),
//                                productEntity.imageUrl.as("image"),
//                                productEntity.price.as("price"),
//                                productEntity.favoriteCount.as("favoriteCount"),
//                                productEntity.releaseTimes.as("releaseTimes"),
//                                bakeryEntity.isActive.as("isBakeryActive"),
//                                productEntity.isActive.as("isProductActive"),
//                                distanceExpression.as("distance")
//                        )
//                )
//                .from(productFavoriteEntity)
//                .innerJoin(productEntity).on(productEntity.id.eq(productFavoriteEntity.id.productId))
//                .innerJoin(bakeryEntity).on(bakeryEntity.id.eq(productEntity.bakeryId))
//                .where(
//                        productFavoriteEntity.id.customerId.eq(customerId),
//                        productFavoriteEntity.isActive.eq(true)
//                )
//                .orderBy(getOrderSpecifiers(criteria.sortType(), distanceExpression));
//    }
//
//    private JPAQuery<Long> createCountQuery(Long customerId) {
//        return queryFactory.select(productFavoriteEntity.count())
//                .from(productFavoriteEntity)
//                .where(
//                        productFavoriteEntity.id.customerId.eq(customerId),
//                        productFavoriteEntity.isActive.eq(true)
//                );
//    }
//
//    private NumberExpression<Double> buildDistanceExpression(ProductFavoriteSearchCriteria criteria) {
//        var bakeryLocation = DistanceExpressionProvider.Location.of(bakeryEntity.latitude, bakeryEntity.longitude);
//        return distanceExpressionProvider.buildDistanceExpression(criteria.location(), bakeryLocation);
//    }
//
//    private OrderSpecifier<?>[] getOrderSpecifiers(SortType sortType, NumberExpression<Double> distanceExpression) {
//        return switch (sortType) {
//            case DISTANCE -> new OrderSpecifier<?>[]{distanceExpression.asc(), productEntity.id.asc()};
//            case POPULAR -> new OrderSpecifier<?>[]{productEntity.favoriteCount.desc(), productEntity.id.asc()};
//            default -> new OrderSpecifier<?>[]{productFavoriteEntity.createdAt.desc(), productEntity.id.asc()}; // 최신순
//        };
//    }
//}
