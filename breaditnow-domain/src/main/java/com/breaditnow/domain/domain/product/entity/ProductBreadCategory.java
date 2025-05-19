package com.breaditnow.domain.domain.product.entity;

import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

import com.breaditnow.domain.domain.breadcategory.entity.BreadCategory;
import com.breaditnow.domain.global.entity.BaseEntity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@Table(name = "P_ProductBreadCategory")
public class ProductBreadCategory extends BaseEntity {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bread_category_id", nullable = false)
	private BreadCategory breadCategory;

	@Builder
	public ProductBreadCategory(Product product, BreadCategory breadCategory) {
		this.product = product;
		this.breadCategory = breadCategory;
		this.product.getBreadCategories().add(this);
	}
}
