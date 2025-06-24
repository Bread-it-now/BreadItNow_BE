package com.breaditnow.owner.product.application.port.in;

import com.breaditnow.owner.product.infrastructure.adapter.in.presentation.request.ProductCreateRequest;
import org.springframework.web.multipart.MultipartFile;

public interface CreateProductUseCase {
    Long createProduct(Long ownerId, Long bakeryId, ProductCreateRequest request, MultipartFile productImage);
}
