package com.breaditnow.product.application;

import com.breaditnow.bakery.domain.model.Bakery;
import com.breaditnow.bakery.domain.model.Image;
import com.breaditnow.common.domain.DailyTime;
import com.breaditnow.image.application.port.in.ImagePort;
import com.breaditnow.owner.application.OwnerDomainProvider;
import com.breaditnow.product.application.port.dto.event.ProductCreatedEvent;
import com.breaditnow.product.application.port.dto.event.ProductUpdatedEvent;
import com.breaditnow.product.application.port.in.CreateProductUseCase;
import com.breaditnow.product.application.port.in.UpdateProductUseCase;
import com.breaditnow.product.application.port.out.ProductRepositoryPort;
import com.breaditnow.product.application.port.out.PublishProductEventPort;
import com.breaditnow.product.domain.Classification;
import com.breaditnow.product.domain.Product;
import com.breaditnow.product.domain.ProductInfo;
import com.breaditnow.product.domain.SalesPolicy;
import com.breaditnow.product.infrastructure.adapter.in.presentation.request.ProductCreateRequest;
import com.breaditnow.product.infrastructure.adapter.in.presentation.request.ProductUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductCommandService implements CreateProductUseCase, UpdateProductUseCase{
    private final OwnerDomainProvider ownerDomainProvider;
    private final ProductRepositoryPort productRepositoryPort;
    private final ImagePort imagePort;
    private final PublishProductEventPort eventPort;

    @Override
    @Transactional
    public Long createProduct(Long ownerId, Long bakeryId, ProductCreateRequest request, MultipartFile productImage) {
        Bakery bakery = ownerDomainProvider.getValidatedBakery(ownerId, bakeryId);

        Image image = imagePort.saveImage(productImage);
        ProductInfo productInfo = ProductInfo.create(request.name(), request.description(), image);
        SalesPolicy salesPolicy = SalesPolicy.create(request.price());
        Classification classification = Classification.create(request.productType());
        Integer lastDisplayOrder = productRepositoryPort.findLastDisplayOrderByBakeryId(bakeryId) + 1;
        List<DailyTime> releaseTimes = request.toDailyTimes();

        Product product = bakery.createProduct(ownerId, bakeryId, productInfo, lastDisplayOrder, salesPolicy, classification, releaseTimes);

        Product savedProduct = productRepositoryPort.save(product);
        eventPort.publishProductCreated(ProductCreatedEvent.from(savedProduct));

        return savedProduct.getId();
    }

    @Override
    @Transactional
    public void updateProduct(Long ownerId, Long bakeryId, Long productId, ProductUpdateRequest request, MultipartFile productImage) {
        Product product = ownerDomainProvider.getValidatedProduct(ownerId, bakeryId, productId);

        Image newImage = imagePort.saveImage(productImage);

        ProductInfo newProductInfo = ProductInfo.create(request.name(), request.description(), newImage);
        SalesPolicy newSalesPolicy = SalesPolicy.create(request.price());
        Classification newClassification = Classification.create(request.productType());
        List<DailyTime> newReleaseTimes = request.toDailyTimes();

        product.update(newProductInfo, newSalesPolicy, newClassification, newReleaseTimes);
        productRepositoryPort.save(product);

        eventPort.publishProductUpdated(ProductUpdatedEvent.from(product));
    }
}
