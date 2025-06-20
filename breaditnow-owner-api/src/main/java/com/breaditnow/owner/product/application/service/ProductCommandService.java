package com.breaditnow.owner.product.application.service;

import com.breaditnow.owner.bakery.application.port.out.ImagePort;
import com.breaditnow.owner.bakery.domain.Bakery;
import com.breaditnow.owner.bakery.domain.Image;
import com.breaditnow.owner.common.domain.DailyTime;
import com.breaditnow.owner.product.application.port.in.CreateProductUseCase;
import com.breaditnow.owner.product.application.port.in.UpdateProductUseCase;
import com.breaditnow.owner.product.application.port.out.ProductRepository;
import com.breaditnow.owner.product.domain.Classification;
import com.breaditnow.owner.product.domain.Product;
import com.breaditnow.owner.product.domain.ProductInfo;
import com.breaditnow.owner.product.domain.SalesPolicy;
import com.breaditnow.owner.product.infrastructure.presentation.request.ProductCreateRequest;
import com.breaditnow.owner.product.infrastructure.presentation.request.ProductUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductCommandService implements CreateProductUseCase, UpdateProductUseCase{
    private final ProductRepository productRepository;
    private final ImagePort imagePort;
    private final OwnershipValidator validator;

    @Override
    @Transactional
    public Long createProduct(Long ownerId, Long bakeryId, ProductCreateRequest request, MultipartFile productImage) {
        Bakery bakery = validator.getValidatedBakery(ownerId, bakeryId);

        Image image = imagePort.saveImage(productImage);
        ProductInfo productInfo = ProductInfo.create(request.name(), request.description(), image);
        SalesPolicy salesPolicy = SalesPolicy.create(request.price());
        Classification classification = Classification.create(request.productType(), request.breadCategoryIds());
        Integer lastDisplayOrder = productRepository.findLastDisplayOrderByBakeryId(bakeryId) + 1;
        List<DailyTime> releaseTimes = request.toDailyTimes();

        Product product = bakery.createProduct(ownerId, bakeryId, productInfo, lastDisplayOrder, salesPolicy, classification, releaseTimes);
        return productRepository.save(product);
    }

    @Override
    @Transactional
    public void updateProduct(Long ownerId, Long bakeryId, Long productId, ProductUpdateRequest request, MultipartFile productImage) {
        Product product = validator.getValidatedProduct(ownerId, bakeryId, productId);

        Image newImage = imagePort.saveImage(productImage);

        ProductInfo newProductInfo = ProductInfo.create(request.name(), request.description(), newImage);
        SalesPolicy newSalesPolicy = SalesPolicy.create(request.price());
        Classification newClassification = Classification.create(request.productType(), request.breadCategoryIds());
        List<DailyTime> newReleaseTimes = request.toDailyTimes();

        product.update(newProductInfo, newSalesPolicy, newClassification, newReleaseTimes);
        productRepository.save(product);
    }
}
