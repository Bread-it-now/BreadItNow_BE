package com.breaditnow.domain.bakery.entity;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

import java.util.ArrayList;
import java.util.List;

import com.breaditnow.domain.bakery.enumerate.OperatingStatus;
import com.breaditnow.domain.owner.entity.Owner;
import com.breaditnow.domain.product.entity.Product;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
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

	@OneToOne(fetch = LAZY)
	@JoinColumn(name = "owner_id", nullable = false)
	Owner owner;

	@Column(nullable = false)
	private String name;

	private String phone;

	private String introduction;

	private String profileImage;

	private String openTime;

	@OneToOne(fetch = LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "address_id")
	private Address address;

	@Enumerated(EnumType.STRING)
	private OperatingStatus operatingStatus;

	@OneToMany(mappedBy = "bakery", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<BakeryImage> bakeryImages = new ArrayList<>();

	@OneToMany(mappedBy = "bakery")
	private List<Product> products = new ArrayList<>();

	private boolean isActive = true;

	@Builder
	public Bakery(Owner owner, String name, String phone, String introduction, String profileImage, String openTime,
		Address address, List<BakeryImage> bakeryImages, OperatingStatus operatingStatus) {
		this.owner = owner;
		this.name = name;
		this.phone = phone;
		this.introduction = introduction;
		this.profileImage = profileImage;
		this.bakeryImages = bakeryImages;
		this.openTime = openTime;
		this.address = address;
		this.operatingStatus = operatingStatus;
	}

	public void update(Bakery bakery) {
		this.owner = bakery.getOwner();
		this.name = bakery.getName();
		this.phone = bakery.getPhone();
		this.introduction = bakery.getIntroduction();

		this.profileImage = bakery.getProfileImage();
		this.openTime = bakery.getOpenTime();
		this.address = bakery.getAddress();
		this.operatingStatus = bakery.getOperatingStatus();

		this.bakeryImages.clear();
		this.bakeryImages.addAll(bakery.getBakeryImages());
	}

	public void updateActive(boolean isActive) {
		this.isActive = isActive;
	}
}
