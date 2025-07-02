package com.breaditnow.product.application;

import com.breaditnow.owner.application.OwnerDomainProvider;
import com.breaditnow.product.application.dto.request.ProductsDeleteRequest;
import com.breaditnow.product.domain.model.Product;
import com.breaditnow.product.domain.port.in.DeleteProductUseCase;
import com.breaditnow.product.domain.port.in.DeleteProductsUseCase;
import com.breaditnow.product.domain.port.out.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductDeletionService implements DeleteProductUseCase, DeleteProductsUseCase {
    private final OwnerDomainProvider ownerDomainProvider;
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public void deleteProduct(Long ownerId, Long bakeryId, Long productId) {
        Product product = ownerDomainProvider.getValidatedProduct(ownerId, bakeryId, productId);
        product.delete();
        productRepository.save(product);
    }


    @Override
    @Transactional
    public void deleteProducts(Long ownerId, Long bakeryId, ProductsDeleteRequest request) {
        List<Product> products = ownerDomainProvider.getValidatedProducts(ownerId, bakeryId, request.productIds());
        products.forEach(Product::delete);
        productRepository.saveAll(products);
    }
}
