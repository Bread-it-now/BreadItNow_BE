package com.breaditnow.owner.product.application;

import com.breaditnow.owner.owner.application.OwnerDomainProvider;
import com.breaditnow.owner.product.application.port.dto.event.ProductDeletedEvent;
import com.breaditnow.owner.product.application.port.in.DeleteProductUseCase;
import com.breaditnow.owner.product.application.port.in.DeleteProductsUseCase;
import com.breaditnow.owner.product.application.port.out.ProductRepository;
import com.breaditnow.owner.product.application.port.out.PublishProductEventPort;
import com.breaditnow.owner.product.domain.Product;
import com.breaditnow.owner.product.infrastructure.adapter.in.presentation.request.ProductsDeleteRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductDeletionService implements DeleteProductUseCase, DeleteProductsUseCase {
    private final OwnerDomainProvider ownerDomainProvider;
    private final ProductRepository productRepository;
    private final PublishProductEventPort eventPort;

    @Override
    @Transactional
    public void deleteProduct(Long ownerId, Long bakeryId, Long productId) {
        Product product = ownerDomainProvider.getValidatedProduct(ownerId, bakeryId, productId);
        product.delete();
        productRepository.save(product);
        eventPort.publishProductDeleted(ProductDeletedEvent.from(product));
    }


    @Override
    @Transactional
    public void deleteProducts(Long ownerId, Long bakeryId, ProductsDeleteRequest request) {
        List<Product> products = ownerDomainProvider.getValidatedProducts(ownerId, bakeryId, request.productIds());
        products.forEach(Product::delete);
        productRepository.saveAll(products);
        products.forEach(product -> eventPort.publishProductDeleted(ProductDeletedEvent.from(product)));
    }
}
