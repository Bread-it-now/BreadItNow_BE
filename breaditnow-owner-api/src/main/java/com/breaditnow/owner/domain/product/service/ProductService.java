package com.breaditnow.owner.domain.product.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.breaditnow.domain.domain.bakery.entity.Bakery;
import com.breaditnow.domain.domain.bakery.repository.BakeryRepository;
import com.breaditnow.domain.domain.product.entity.Product;
import com.breaditnow.domain.domain.product.repository.ProductRepository;
import com.breaditnow.owner.domain.product.controller.req.ProductCreateRequest;
import com.breaditnow.owner.global.s3.upload.FileUploader;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

	private final BakeryRepository bakeryRepository;
	private final ProductRepository productRepository;
	private final FileUploader uploader;

	@Transactional
	public Long createProduct(Long ownerId, Long bakeryId, ProductCreateRequest request, MultipartFile productImage) {
		Bakery bakery = bakeryRepository.getByOwnerIdAndId(ownerId, bakeryId);

		String productImageUrl = uploadFile(productImage, "image/owner/product");
		Product product = request.toEntity(bakery, productImageUrl);

		// 카테고리 저장 필요

		Product savedProduct = productRepository.save(product);
		return savedProduct.getId();
	}

	private String uploadFile(MultipartFile file, String path) {
		if (file != null && !file.isEmpty()) {
			return uploader.upload(file, path);
		}
		return "";
	}
}
