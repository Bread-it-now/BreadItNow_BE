package com.breaditnow.product.application.port.in;

import com.breaditnow.product.infrastructure.adapter.in.presentation.request.ProductCreateRequest;
import org.springframework.web.multipart.MultipartFile;

public interface CreateProductUseCase {
    Long createProduct(Long ownerId, Long bakeryId, ProductCreateRequest request, MultipartFile productImage);
}
