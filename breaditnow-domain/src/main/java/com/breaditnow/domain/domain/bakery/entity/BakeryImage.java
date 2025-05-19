package com.breaditnow.domain.domain.bakery.entity;

import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

import com.breaditnow.domain.global.entity.BaseEntity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@Table(name = "P_BakeryImage")
public class BakeryImage extends BaseEntity {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bakery_id", nullable = false)
	private Bakery bakery;

	@Column(length = 1000)
	private String imageUrl;

	@Builder
	public BakeryImage(Bakery bakery, String imageUrl) {
		this.bakery = bakery;
		this.imageUrl = imageUrl;
	}
}
