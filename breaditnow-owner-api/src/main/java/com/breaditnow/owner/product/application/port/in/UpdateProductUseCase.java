package com.breaditnow.owner.product.application.port.in;

import com.breaditnow.owner.product.infrastructure.adapter.in.presentation.request.ProductUpdateRequest;
import org.springframework.web.multipart.MultipartFile;

public interface UpdateProductUseCase {
    void updateProduct(Long ownerId, Long bakeryId, Long productId, ProductUpdateRequest request, MultipartFile productImage);
}
