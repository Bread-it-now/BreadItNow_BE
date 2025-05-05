package com.breaditnow.domain.domain.region.repository;

import com.breaditnow.common.client.kakao.dto.AddressNameDto;
import com.breaditnow.domain.domain.region.entity.Region;
import com.breaditnow.domain.domain.region.entity.RegionPK;
import com.breaditnow.domain.global.exception.DomainException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

import static com.breaditnow.domain.global.exception.DomainErrorCode.REGION_NOT_FOUND;

public interface RegionRepository extends JpaRepository<Region, RegionPK> {
    default void checkExists(RegionPK regionPK) {
        if (!existsById(regionPK)) {
            throw new DomainException(REGION_NOT_FOUND);
        }
    }

    default Region getById(RegionPK regionPK) {
        return findById(regionPK)
                .orElseThrow(() -> new DomainException(REGION_NOT_FOUND));
    }

    default Region getRegionByAddress(AddressNameDto addressNameDto) {
        return findBySidoNameAndGugunNameAndDongName(addressNameDto.getSidoName(), addressNameDto.getGugunName(), addressNameDto.getDongName())
                .orElseThrow(() -> new DomainException(REGION_NOT_FOUND));
    }

    @Query("SELECT DISTINCT r.id.sidoCode, r.sidoName FROM Region r ORDER BY r.id.sidoCode")
    List<Object[]> findSidoList();

    @Query("SELECT DISTINCT r.sidoName, r.id.gugunCode, r.gugunName FROM Region r WHERE r.id.sidoCode = :sidoCode ORDER BY r.id.gugunCode")
    List<Object[]> getGugunListBySido(@Param("sidoCode") String sidoCode);

    Optional<Region> findBySidoNameAndGugunNameAndDongName(String sidoName, String gugunName, String dongName);

}

