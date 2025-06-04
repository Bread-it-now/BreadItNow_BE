package com.breaditnow.customer.product.infrastructure;

import com.breaditnow.customer.customer.domain.Customer;
import com.breaditnow.customer.product.application.port.LoadProductFavoritePort;
import com.breaditnow.customer.product.application.port.SaveProductFavoritePort;
import com.breaditnow.customer.product.domain.Product;
import com.breaditnow.customer.product.infrastructure.jpa.JpaProductFavoriteRepository;
import com.breaditnow.customer.product.infrastructure.jpa.JpaProductRepository;
import com.breaditnow.customer.product.infrastructure.jpa.ProductFavoriteEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductFavoriteAdapter implements SaveProductFavoritePort, LoadProductFavoritePort {
    private final JpaProductRepository jpaProductRepository;
    private final JpaProductFavoriteRepository jpaProductFavoriteRepository;

    @Override
    public void save(Customer customer, Product product) {
        ProductFavoriteEntity productFavoriteEntity = new ProductFavoriteEntity(customer, product);
        jpaProductFavoriteRepository.save(productFavoriteEntity);
        jpaProductRepository.updateFavoriteProductCount(product.getId());
    }
}
