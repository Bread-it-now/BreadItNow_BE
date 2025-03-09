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
	private final ProductBreadCategoryService productBreadCategoryService;

	@Transactional
	public Long createProduct(Long ownerId, Long bakeryId, ProductCreateRequest request, MultipartFile productImage) {
		Bakery bakery = bakeryRepository.getByOwnerIdAndId(ownerId, bakeryId);

		String productImageUrl = uploadFile(productImage, "image/owner/product");
		Product product = request.toEntity(bakery, productImageUrl);
		Product savedProduct = productRepository.save(product);

		Long[] breadCategoryIds = request.breadCategoryIds();
		if (breadCategoryIds != null) {
			productBreadCategoryService.addProductBreadCategories(breadCategoryIds, savedProduct);
		}

		return savedProduct.getId();
	}

	private String uploadFile(MultipartFile file, String path) {
		if (file != null && !file.isEmpty()) {
			return uploader.upload(file, path);
		}
		return "";
	}

	@Transactional
	public Long deleteProduct(Long ownerId, Long bakeryId, Long productId) {
		Bakery bakery = bakeryRepository.getByOwnerIdAndId(ownerId, bakeryId);
		Product product = productRepository.getByBakeryIdAndId(bakery.getId(), productId);
		product.updateActive(false);
		return product.getId();
	}
}
