package com.breaditnow.domain.domain.customer.entity;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

import com.breaditnow.domain.domain.region.entity.Region;
import com.breaditnow.domain.global.entity.BaseEntity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PRIVATE)
@Table(name = "P_CustomerRegionPreference")
public class CustomerRegionPreference extends BaseEntity {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id", nullable = false)
	private Customer customer;

	@ManyToOne(fetch = LAZY)
	@JoinColumns({
		@JoinColumn(name = "sido_code", referencedColumnName = "sidoCode"),
		@JoinColumn(name = "gugun_code", referencedColumnName = "gugunCode"),
		@JoinColumn(name = "dong_code", referencedColumnName = "dongCode")
	})
	private Region region;

	@Builder
	public CustomerRegionPreference(Customer customer, Region region) {
		this.customer = customer;
		this.region = region;
	}
}
