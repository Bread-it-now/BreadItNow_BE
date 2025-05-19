package com.breaditnow.domain.domain.bakery.entity;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

import java.util.ArrayList;
import java.util.List;

import com.breaditnow.domain.domain.bakery.enumerate.OperatingStatus;
import com.breaditnow.domain.domain.owner.entity.Owner;
import com.breaditnow.domain.domain.product.entity.Product;
import com.breaditnow.domain.global.entity.BaseEntity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@ToString
@Table(name = "P_Bakery")
public class Bakery extends BaseEntity {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@ManyToOne(fetch = LAZY)
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

	@OneToMany(mappedBy = "bakery", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<BakeryImage> additionalImages = new ArrayList<>();

	@OneToMany(mappedBy = "bakery")
	private List<Product> products = new ArrayList<>();

	private boolean isActive = true;

	@Builder
	public Bakery(Owner owner, String name, String phone, String introduction, String profileImage, String openTime,
		Address address, List<BakeryImage> additionalImages, OperatingStatus operatingStatus) {
		this.owner = owner;
		this.name = name;
		this.phone = phone;
		this.introduction = introduction;
		this.profileImage = profileImage;
		this.additionalImages = additionalImages;
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

		this.additionalImages.clear();
		this.additionalImages.addAll(bakery.getAdditionalImages());
	}

	public void changeIsActive(boolean isActive) {
		this.isActive = isActive;
	}

	public void updateOperatingStatus(OperatingStatus operatingStatus) {
		this.operatingStatus = operatingStatus;
	}

	public void updateProfileImage(String profileImageUrl) {
		this.profileImage = profileImageUrl;
	}
}
