package com.breaditnow.mysql.domain.bakery.repository;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.breaditnow.mysql.domain.bakery.entity.Address;
import com.breaditnow.mysql.domain.bakery.entity.Bakery;
import com.breaditnow.mysql.domain.bakery.enumerate.OperatingStatus;
import com.breaditnow.mysql.domain.owner.entity.Owner;
import com.breaditnow.mysql.domain.owner.repository.OwnerRepository;

@SpringBootTest
@Transactional
class BakeryRepositoryTest {
	@Autowired
	private BakeryRepository bakeryRepository;
	@Autowired
	private OwnerRepository ownerRepository;

	@Test
	public void testSaveAndFindBakery() {
		// given
		Address address = Address.createAddressBuilder()
			.city("서울특별시")
			.region("강남구")
			.zipcode("12345")
			.latitude(37.1234)
			.longitude(127.5678)
			.description("강남대로 123")
			.build();

		Owner owner = Owner.createOwnerBuilder()
			.email("owner@example.com")
			.password("ownerPassword")
			.build();
		ownerRepository.save(owner); // Owner 객체 먼저 저장

		Bakery bakery = Bakery.createBakeryBuilder()
			.name("빵집 이름")
			.phone("010-1234-5678")
			.introduction("맛있는 빵집입니다.")
			.profileImage("http://example.com/profile.jpg")
			.openTime("09:00 - 21:00")
			.address(address)
			.operatingStatus(OperatingStatus.OPEN)
			.build();

		bakeryRepository.save(bakery);

		// when
		Bakery foundBakery = bakeryRepository.findById(bakery.getId()).orElse(null);

		// then
		assertThat(foundBakery).isNotNull();
		assertThat(foundBakery.getName()).isEqualTo("빵집 이름");
		assertThat(foundBakery.getAddress().getCity()).isEqualTo("서울특별시");
		// assertThat(foundBakery.getOwner().getEmail()).isEqualTo("owner@example.com");
	}
}
