package com.breaditnow.customer.domain.region.service;

import com.breaditnow.customer.domain.region.controller.res.GugunResponse;
import com.breaditnow.customer.domain.region.controller.res.SidoResponse;
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
        List<Object[]> results = regionRepository.getGugunListBySido(sidoCode);
        return results.stream()
                .map(result -> new GugunResponse((String) result[0], (String) result[1], (String) result[2]))
                .toList();
    }
}
