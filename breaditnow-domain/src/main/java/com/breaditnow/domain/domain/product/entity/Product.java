package com.breaditnow.domain.domain.product.entity;

import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

import java.util.ArrayList;
import java.util.List;

import com.breaditnow.domain.domain.bakery.entity.Bakery;
import com.breaditnow.domain.domain.product.enumerate.ProductType;
import com.breaditnow.domain.global.entity.BaseEntity;

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
@ToString(exclude = {"bakery", "breadCategories"})
public class Product extends BaseEntity {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bakery_id", nullable = false)
	private Bakery bakery;

	@Enumerated(EnumType.STRING)
	private ProductType type;

	@Column(nullable = false)
	private String name;

	private Integer price;

	private String image;

	private String description;

	private String releaseTime;

	private Integer stock;

	private boolean isActive;

	private Integer displayOrder;

	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ProductBreadCategory> breadCategories = new ArrayList<>();

	@Builder
	public Product(Bakery bakery, ProductType type, String name, Integer price, String image, String description,
		Integer stock,
		String releaseTime, Boolean isActive) {
		this.bakery = bakery;
		this.type = type;
		this.name = name;
		this.price = price;
		this.image = image;
		this.description = description;
		this.releaseTime = releaseTime;
		this.stock = stock;
		this.isActive = isActive;
	}

	public void update(Product product) {
		this.bakery = product.getBakery();
		this.type = product.getType();
		this.name = product.getName();
		this.price = product.getPrice();
		this.image = product.getImage();
		this.description = product.getDescription();
		this.releaseTime = product.getReleaseTime();
	}

	public void updateActive(boolean isActive) {
		this.isActive = isActive;
	}

	public void updateDisplayOrder(int order) {
		this.displayOrder = order;
	}

	public void updateStock(int stock) {
		this.stock = stock;
	}
}
