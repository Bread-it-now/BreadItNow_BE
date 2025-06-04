package com.breaditnow.customer.product.application;

import com.breaditnow.customer.product.application.port.LoadProductFavoritePort;
import com.breaditnow.customer.product.application.port.SaveProductFavoritePort;
import com.breaditnow.customer.product.domain.Product;
import com.breaditnow.customer.product.domain.ProductFavorite;
import com.breaditnow.domain.global.exception.DomainException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.breaditnow.domain.global.exception.DomainErrorCode.BREAD_FAVORITE_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ProductFavoriteService {
    private final SaveProductFavoritePort saveProductFavoritePort;
    private final LoadProductFavoritePort loadProductFavoritePort;
    private final ProductService productService;

    public void addFavoriteProduct(Long customerId, Long productId) {
        Product product = productService.loadProduct(productId);
        Optional<ProductFavorite> OptionalProductFavorite = loadProductFavoritePort.loadProductFavorite(customerId, product.getId());
        if (OptionalProductFavorite.isEmpty()) {
            ProductFavorite productFavorite = new ProductFavorite(customerId, productId, true);
            saveProductFavoritePort.save(productFavorite);
        }
        else{
            ProductFavorite productFavorite = OptionalProductFavorite.get();
            productFavorite.activate();
            saveProductFavoritePort.save(productFavorite);
        }
    }

    public void removeFavoriteProduct(Long customerId, Long productId) {
        Product product = productService.loadProduct(productId);
        Optional<ProductFavorite> optionalProductFavorite = loadProductFavoritePort.loadProductFavorite(customerId, product.getId());
        if(optionalProductFavorite.isEmpty()) {
            throw new DomainException(BREAD_FAVORITE_NOT_FOUND);
        }
        ProductFavorite productFavorite = optionalProductFavorite.get();
        productFavorite.deactivate();
        saveProductFavoritePort.delete(productFavorite);
    }
}
