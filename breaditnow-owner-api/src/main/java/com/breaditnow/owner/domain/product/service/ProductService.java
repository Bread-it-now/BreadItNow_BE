package com.breaditnow.owner.domain.product.service;

import com.breaditnow.domain.domain.bakery.entity.Bakery;
import com.breaditnow.domain.domain.bakery.repository.BakeryRepository;
import com.breaditnow.domain.domain.product.entity.Product;
import com.breaditnow.domain.domain.product.repository.ProductRepository;
import com.breaditnow.external.domain.s3.FileUploaderService;
import com.breaditnow.owner.domain.product.controller.req.ProductCreateRequest;
import com.breaditnow.owner.domain.product.controller.req.ProductOrderItemRequest;
import com.breaditnow.owner.domain.product.controller.req.ProductUpdateRequest;
import com.breaditnow.owner.domain.product.controller.res.ProductListResponse;
import com.breaditnow.owner.domain.product.controller.res.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private static final String PRODUCT_IMAGE_PATH = "image/owner/";

    private final BakeryRepository bakeryRepository;
    private final ProductRepository productRepository;
    private final FileUploaderService uploaderService;
    private final ProductBreadCategoryService productBreadCategoryService;

    @Transactional
    public Long createProduct(Long ownerId, Long bakeryId, ProductCreateRequest request, MultipartFile productImage) {
        Bakery bakery = bakeryRepository.getByOwnerIdAndId(ownerId, bakeryId);

        String productImageUrl = uploadFile(productImage,
                PRODUCT_IMAGE_PATH + ownerId + "/bakery/" + bakeryId + "/products");
        Product product = request.toEntity(bakery, productImageUrl);
        Product savedProduct = productRepository.save(product);

        int nextDisplayOrder = productRepository.findMaxDisplayOrderByBakeryId(bakery.getId()) + 1;
        savedProduct.updateDisplayOrder(nextDisplayOrder);

        Long[] breadCategoryIds = request.breadCategoryIds();
        if (breadCategoryIds != null) {
            productBreadCategoryService.addProductBreadCategories(breadCategoryIds, savedProduct);
        }
        return savedProduct.getId();
    }

    @Transactional
    public ProductResponse updateProduct(Long ownerId, Long bakeryId, Long productId, ProductUpdateRequest request,
                                         MultipartFile productImage) {
        Bakery bakery = bakeryRepository.getByOwnerIdAndId(ownerId, bakeryId);
        Product product = productRepository.getByBakeryIdAndId(bakeryId, productId);

        String updatedProductImageUrl = uploadFile(productImage, PRODUCT_IMAGE_PATH);
        Product updatedProduct = request.toEntity(bakery, updatedProductImageUrl);
        product.update(updatedProduct);

        Long[] breadCategoryIds = request.breadCategoryIds();
        if (breadCategoryIds != null) {
            product.getBreadCategories().clear();
            productBreadCategoryService.addProductBreadCategories(breadCategoryIds, product);
        }

        return ProductResponse.of(product);
    }

    @Transactional
    public Long deleteProduct(Long ownerId, Long bakeryId, Long productId) {
        Bakery bakery = bakeryRepository.getByOwnerIdAndId(ownerId, bakeryId);
        Product product = productRepository.getByBakeryIdAndId(bakery.getId(), productId);
        product.updateActive(false);
        product.updateDisplayOrder(-1);
        return product.getId();
    }

    @Transactional
    public int deleteProducts(Long ownerId, Long bakeryId, List<Long> productIds) {
        Bakery bakery = bakeryRepository.getByOwnerIdAndId(ownerId, bakeryId);

        int deletedCount = 0;
        for (Long productId : productIds) {
            Product product = productRepository.getByBakeryIdAndId(bakery.getId(), productId);
            product.updateActive(false);
            product.updateDisplayOrder(-1);
            deletedCount++;
        }
        return deletedCount;
    }

    @Transactional
    public void updateHiddenProducts(Long ownerId, Long bakeryId, List<Long> productIds, boolean isHidden) {
        Bakery bakery = bakeryRepository.getByOwnerIdAndId(ownerId, bakeryId);
        int nextDisplayOrder = productRepository.findMaxDisplayOrderByBakeryId(bakery.getId()) + 1;

        for (Long productId : productIds) {
            Product product = productRepository.getByBakeryIdAndId(bakery.getId(), productId);
            if (isHidden) {
                product.hide();
            } else {
                product.unhide(nextDisplayOrder++);
            }
        }
    }

    public ProductResponse getProduct(Long ownerId, Long bakeryId, Long productId) {
        Bakery bakery = bakeryRepository.getByOwnerIdAndId(ownerId, bakeryId);
        Product product = productRepository.getByBakeryIdAndId(bakery.getId(), productId);
        return ProductResponse.of(product);
    }

    public ProductListResponse getProducts(Long ownerId, Long bakeryId) {
        Bakery bakery = bakeryRepository.getByOwnerIdAndId(ownerId, bakeryId);
        List<Product> products = productRepository.findActiveByBakeryId(bakery.getId());
        return ProductListResponse.of(products);
    }

    @Transactional
    public void updateProductOrder(Long ownerId, Long bakeryId, List<ProductOrderItemRequest> orderItems) {
        Bakery bakery = bakeryRepository.getByOwnerIdAndId(ownerId, bakeryId);

        for (ProductOrderItemRequest item : orderItems) {
            Product product = productRepository.getByBakeryIdAndId(bakery.getId(), item.productId());
            product.updateDisplayOrder(item.order());
        }
    }

    @Transactional
    public Integer updateProductStock(Long ownerId, Long bakeryId, Long productId, Integer stock) {
        Bakery bakery = bakeryRepository.getByOwnerIdAndId(ownerId, bakeryId);
        Product product = productRepository.getByBakeryIdAndId(bakery.getId(), productId);
        product.updateStock(stock);
        return product.getStock();
    }

    private String uploadFile(MultipartFile file, String path) {
        if (file != null && !file.isEmpty()) {
            return uploaderService.upload(file, path);
        }
        return "";
    }
}
