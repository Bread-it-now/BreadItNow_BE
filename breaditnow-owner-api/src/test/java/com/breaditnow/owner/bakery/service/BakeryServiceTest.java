package com.breaditnow.owner.bakery.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.breaditnow.domain.bakery.repository.BakeryRepository;
import com.breaditnow.domain.owner.entity.Owner;
import com.breaditnow.domain.owner.repository.OwnerRepository;
import com.breaditnow.domain.region.repository.RegionRepository;
import com.breaditnow.owner.bakery.controller.req.BakeryCreateRequest;

@SpringBootTest
@Transactional
class BakeryServiceTest {
	@Autowired
	private BakeryService bakeryService;
	@Autowired
	private OwnerRepository ownerRepository;
	@Autowired
	private RegionRepository regionRepository;
	@Autowired
	private BakeryRepository bakeryRepository;

	private BakeryCreateRequest bakeryCreateRequest;

	@Test
	@Rollback(value = false)
	void create_bakery() {
		// given
		Owner owner = new Owner("email", "password");
		ownerRepository.save(owner);

		bakeryCreateRequest = new BakeryCreateRequest(
			"맛있는 빵집",
			"1111010100",
			"서울시 종로구 청운동",
			"02-1234-5678",
			"09:00-21:00",
			"최고의 빵을 제공합니다.",
			"default.png"
		);

		Long bakeyId = bakeryService.createBakery(1L, bakeryCreateRequest);
	}
}
