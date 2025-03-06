package com.breaditnow.customer.domain.region.service;

import com.breaditnow.customer.domain.region.controller.res.DongResponse;
import com.breaditnow.customer.domain.region.controller.res.GugunResponse;
import com.breaditnow.customer.domain.region.controller.res.SidoResponse;
import com.breaditnow.domain.domain.region.entity.Region;
import com.breaditnow.domain.domain.region.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class RegionService {
    private final RegionRepository regionRepository;

    public List<SidoResponse> getSidoList() {
        List<Object[]> results = regionRepository.findSidoList();
        return results.stream()
                .map(result -> new SidoResponse((String) result[0], (String) result[1]))
                .collect(Collectors.toList());
    }

    public List<GugunResponse> getGugunListBySido(String sidoCode) {
        List<Region> regions = regionRepository.findDistinctById_SidoCodeOrderById_GugunCode(sidoCode);
        return regions.stream().map(GugunResponse::of).collect(Collectors.toList());
    }

    public List<DongResponse> getDongListByGugun(String gugunCode) {
        List<Region> regions = regionRepository.findById_GugunCodeOrderById_DongCode(gugunCode);
        return regions.stream().map(DongResponse::of).collect(Collectors.toList());
    }
}
