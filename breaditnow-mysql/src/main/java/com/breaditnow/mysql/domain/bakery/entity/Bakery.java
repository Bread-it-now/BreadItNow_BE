package com.breaditnow.mysql.domain.bakery.entity;

import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

import com.breaditnow.mysql.domain.bakery.enumerate.OperatingStatus;
import com.breaditnow.mysql.domain.owner.entity.Owner;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@ToString
public class Bakery {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "owner_id")
	Owner owner;

	private String name; // 빵집 이름

	private String phone; // 전화번호

	private String introduction; // 빵집 소개글

	private String profileImage; // 빵집 프로필 이미지 URL

	private String openTime; // 오픈 시간

	@Embedded
	private Address address; // 주소

	@Enumerated(EnumType.STRING)
	private OperatingStatus operatingStatus; // 운영 상태

	@Builder(builderMethodName = "createBakeryBuilder")
	public Bakery(Owner owner, String name, String phone, String introduction, String profileImage, String openTime,
		Address address, OperatingStatus operatingStatus) {
		this.owner = owner;
		this.name = name;
		this.phone = phone;
		this.introduction = introduction;
		this.profileImage = profileImage;
		this.openTime = openTime;
		this.address = address;
		this.operatingStatus = operatingStatus;
	}
}
