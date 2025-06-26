package com.breaditnow.product.domain.port.in;

import com.breaditnow.product.adapter.in.dto.request.ProductUpdateRequest;
import org.springframework.web.multipart.MultipartFile;

public interface UpdateProductUseCase {
    void updateProduct(Long ownerId, Long bakeryId, Long productId, ProductUpdateRequest request, MultipartFile productImage);
}
