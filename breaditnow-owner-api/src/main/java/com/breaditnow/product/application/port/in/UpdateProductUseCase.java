package com.breaditnow.product.application.port.in;

import com.breaditnow.product.infrastructure.adapter.in.presentation.request.ProductUpdateRequest;
import org.springframework.web.multipart.MultipartFile;

public interface UpdateProductUseCase {
    void updateProduct(Long ownerId, Long bakeryId, Long productId, ProductUpdateRequest request, MultipartFile productImage);
}
