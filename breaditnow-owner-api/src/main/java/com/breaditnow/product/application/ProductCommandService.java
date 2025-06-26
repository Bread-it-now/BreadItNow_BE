package com.breaditnow.product.application;

import com.breaditnow.bakery.domain.model.Bakery;
import com.breaditnow.common.domain.DailyTime;
import com.breaditnow.image.application.port.in.ImageUseCase;
import com.breaditnow.image.domain.Image;
import com.breaditnow.owner.application.OwnerDomainProvider;
import com.breaditnow.product.application.dto.request.ProductCreateRequest;
import com.breaditnow.product.application.dto.request.ProductUpdateRequest;
import com.breaditnow.product.domain.model.Classification;
import com.breaditnow.product.domain.model.Product;
import com.breaditnow.product.domain.model.ProductInfo;
import com.breaditnow.product.domain.model.SalesPolicy;
import com.breaditnow.product.domain.port.in.CreateProductUseCase;
import com.breaditnow.product.domain.port.in.UpdateProductUseCase;
import com.breaditnow.product.domain.port.out.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductCommandService implements CreateProductUseCase, UpdateProductUseCase{
    private final OwnerDomainProvider ownerDomainProvider;
    private final ProductRepository productRepository;
    private final ImageUseCase imageUseCase;

    @Override
    @Transactional
    public Long createProduct(Long ownerId, Long bakeryId, ProductCreateRequest request, MultipartFile productImage) {
        Bakery bakery = ownerDomainProvider.getValidatedBakery(ownerId, bakeryId);

        Image image = imageUseCase.saveImage(productImage);
        ProductInfo productInfo = ProductInfo.create(request.name(), request.description(), image);
        SalesPolicy salesPolicy = SalesPolicy.create(request.price());
        Classification classification = Classification.create(request.productType());
        Integer lastDisplayOrder = productRepository.findLastDisplayOrderByBakeryId(bakeryId) + 1;
        List<DailyTime> releaseTimes = request.toDailyTimes();

        Product product = bakery.createProduct(ownerId, bakeryId, productInfo, lastDisplayOrder, salesPolicy, classification, releaseTimes);

        Product savedProduct = productRepository.save(product);
        return savedProduct.getId();
    }

    @Override
    @Transactional
    public void updateProduct(Long ownerId, Long bakeryId, Long productId, ProductUpdateRequest request, MultipartFile productImage) {
        Product product = ownerDomainProvider.getValidatedProduct(ownerId, bakeryId, productId);

        Image newImage = imageUseCase.saveImage(productImage);

        ProductInfo newProductInfo = ProductInfo.create(request.name(), request.description(), newImage);
        SalesPolicy newSalesPolicy = SalesPolicy.create(request.price());
        Classification newClassification = Classification.create(request.productType());
        List<DailyTime> newReleaseTimes = request.toDailyTimes();

        product.update(newProductInfo, newSalesPolicy, newClassification, newReleaseTimes);
        productRepository.save(product);
    }
}
