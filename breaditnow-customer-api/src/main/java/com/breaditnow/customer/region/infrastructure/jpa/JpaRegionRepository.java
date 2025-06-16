package com.breaditnow.customer.region.infrastructure.jpa;

import com.breaditnow.customer.region.infrastructure.entity.RegionEntity;
import com.breaditnow.customer.region.presentation.res.GugunResponse;
import com.breaditnow.customer.region.presentation.res.SidoResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface JpaRegionRepository extends JpaRepository<RegionEntity, String> {
    boolean existsByRegionCodeStartingWith(String regionCodePrefix);

    @Query("SELECT new com.breaditnow.customer.region.presentation.res.SidoResponse(r.sidoCode, r.sidoName) " +
            "FROM RegionEntity r " +
            "GROUP BY r.sidoCode, r.sidoName")
    List<SidoResponse> findSidoResponses();

    @Query("SELECT new com.breaditnow.customer.region.presentation.res.GugunResponse(r.sidoName, r.gugunCode, r.gugunName) " +
            "FROM RegionEntity r " +
            "WHERE r.sidoCode = :sidoCode " +
            "GROUP BY r.sidoName, r.gugunCode, r.gugunName")
    List<GugunResponse> findGugunResponsesBySidoCode(@Param("sidoCode") String sidoCode);

    Optional<RegionEntity> findBySidoNameAndGugunNameAndDongName(String sidoName, String gugunName, String dongName);
}
