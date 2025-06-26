package com.breaditnow.product.domain.port.in;

import com.breaditnow.product.adapter.in.dto.request.ProductCreateRequest;
import org.springframework.web.multipart.MultipartFile;

public interface CreateProductUseCase {
    Long createProduct(Long ownerId, Long bakeryId, ProductCreateRequest request, MultipartFile productImage);
}
