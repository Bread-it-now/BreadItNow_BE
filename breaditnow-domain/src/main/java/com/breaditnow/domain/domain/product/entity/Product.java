package com.breaditnow.domain.domain.product.entity;

import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

import java.util.ArrayList;
import java.util.List;

import com.breaditnow.domain.domain.bakery.entity.Bakery;
import com.breaditnow.domain.domain.product.enumerate.ProductType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@ToString
public class Product {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id", nullable = false)
	private Bakery bakery;

	@Enumerated(EnumType.STRING)
	private ProductType type;

	@Column(nullable = false)
	private String name;

	private int price;

	private String image;

	private String description;

	private int stock;

	private String releaseTime;

	private boolean isActive;

	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ProductBreadCategory> breadCategories = new ArrayList<>();

	@Builder
	public Product(Bakery bakery, String name, int price, String image, String description, int stock,
		String releaseTime,
		boolean isActive) {
		this.bakery = bakery;
		this.name = name;
		this.price = price;
		this.image = image;
		this.description = description;
		this.stock = stock;
		this.releaseTime = releaseTime;
		this.isActive = isActive;
	}
}
