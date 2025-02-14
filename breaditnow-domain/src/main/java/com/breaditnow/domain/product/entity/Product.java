package com.breaditnow.domain.product.entity;

import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

import com.breaditnow.domain.bakery.entity.Bakery;

import jakarta.persistence.Entity;
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
public class Product {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
	Bakery bakery;

	private String name; // 상품 이름

	private int price; // 가격

	private String image; // 빵 이미지 URL

	private String description; // 상품 설명

	private int stock; // 재고 수량

	private String releaseTime; // 빵 나오는 예상 시간(예: 08:30;12:00;16:00)

	private boolean isActive; // 판매 활성화 여부

	@Builder(builderMethodName = "createProductBuilder")
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
