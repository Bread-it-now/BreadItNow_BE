package com.breaditnow.domain.bakery.entity;

import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

import com.breaditnow.domain.bakery.enumerate.OperatingStatus;
import com.breaditnow.domain.owner.entity.Owner;

import jakarta.persistence.Column;
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
	@JoinColumn(name = "owner_id", nullable = false)
	Owner owner;

	@Column(nullable = false)
	private String name;

	private String phone;

	private String introduction;

	private String profileImage;

	private String openTime;

	@Embedded
	private Address address;

	@Enumerated(EnumType.STRING)
	private OperatingStatus operatingStatus;

	@Builder
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
