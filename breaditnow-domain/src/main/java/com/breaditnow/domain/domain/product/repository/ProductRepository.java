package com.breaditnow.domain.domain.product.repository;

import com.breaditnow.domain.domain.product.entity.Product;
import com.breaditnow.domain.domain.product.enumerate.ProductType;
import com.breaditnow.domain.global.exception.DomainException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

import static com.breaditnow.domain.global.exception.DomainErrorCode.*;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {
    default Product getByTypeAndId(ProductType type, Long id) {
        return findByTypeAndId(type, id)
                .orElseThrow(() -> new DomainException(BREAD_NOT_FOUND));
    }

    Optional<Product> findByTypeAndId(ProductType type, Long id);

    @Query("select coalesce(max(p.displayOrder), 0) from Product p where p.bakery.id = :bakeryId and p.isActive = true")
    int findMaxDisplayOrderByBakeryId(@Param("bakeryId") Long bakeryId);

    @Query("select p from Product p where p.bakery.id = :bakeryId and p.isActive = true order by p.displayOrder asc")
    List<Product> findActiveByBakeryId(@Param("bakeryId") Long bakeryId);

    default Product getById(Long id) {
        return findById(id)
                .orElseThrow(() -> new DomainException(PRODUCT_NOT_FOUND));
    }

    default Product getByIdAndIsActiveTrue(Long id) {
        Product product = getById(id);
        if (!product.isActive()) {
            throw new DomainException(PRODUCT_INACTIVE);
        }
        return product;
    }

    default Product getByBakeryIdAndId(Long bakeryId, Long id) {
        Product product = getByIdAndIsActiveTrue(id);
        if (!product.getBakery().getId().equals(bakeryId)) {
            throw new DomainException(PRODUCT_MISMATCH);
        }
        return product;
    }

    default void checkProductIsAlive(Long id) {
        getByIdAndIsActiveTrue(id);
    }

    // displayOrder = -1이 되면 원래 displayOrder() 뒤에 있던 데이터들의 displayOrder들을 모두 -1 해야 됩니다.
    @Modifying
    @Query("UPDATE Product p SET p.displayOrder = p.displayOrder - 1 WHERE p.bakery.id = :bakeryId AND p.displayOrder > :modifiedDisplayOrder")
    void updateDisplayOrderAfterModification(@Param("bakeryId") Long bakeryId, @Param("modifiedDisplayOrder") int modifiedDisplayOrder);

    // displayOrder를 특정 위치로 이동시킬 때, 기존의 값들이 당겨지고 해당 상품은 그 위치로 이동(displayOrder = 3에서 6으로 수정하는 경우)
    @Modifying
    @Query("UPDATE Product p SET p.displayOrder = p.displayOrder - 1 WHERE p.bakery.id = :bakeryId AND p.displayOrder > :startOrder AND p.displayOrder <= :endOrder")
    void shiftDisplayOrderBeforeMove(@Param("bakeryId") Long bakeryId, @Param("startOrder") int startOrder, @Param("endOrder") int endOrder);

    // displayOrder를 특정 위치로 이동시킬 때, 기존의 값들이 밀리고 해당 상품은 그 위치로 이동(displayOrder = 6에서 3으로 수정하는 경우)
    @Modifying
    @Query("UPDATE Product p SET p.displayOrder = p.displayOrder + 1 WHERE p.bakery.id = :bakeryId AND p.displayOrder < :startOrder AND p.displayOrder >= :endOrder")
    void shiftDisplayOrderAfterMove(@Param("bakeryId") Long bakeryId, @Param("startOrder") int startOrder, @Param("endOrder") int endOrder);

}
