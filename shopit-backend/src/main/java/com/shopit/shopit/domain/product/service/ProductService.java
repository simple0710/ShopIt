package com.shopit.shopit.domain.product.service;

import com.shopit.shopit.domain.product.dto.request.CreateProductRequest;
import com.shopit.shopit.domain.product.dto.response.CreateProductResponse;
import com.shopit.shopit.domain.product.dto.response.ProductDetailResponse;
import com.shopit.shopit.domain.product.dto.response.ProductsPageResponse;
import com.shopit.shopit.domain.product.entity.Product;
import com.shopit.shopit.domain.product.entity.ProductOption;
import com.shopit.shopit.domain.product.exception.ProductNotFoundException;
import com.shopit.shopit.domain.product.exception.ProductOptionRequiredException;
import com.shopit.shopit.domain.product.repository.ProductOptionRepository;
import com.shopit.shopit.domain.product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductOptionRepository productOptionRepository;

    @Transactional
    public CreateProductResponse createProduct(CreateProductRequest request) {
        if (request.getProductOptions() == null || request.getProductOptions().isEmpty()) {
            throw new ProductOptionRequiredException();
        }

        // DTO → Entity 변환
        List<ProductOption> options = request.getProductOptions().stream()
                .map(ProductOption::fromDto)
                .collect(Collectors.toCollection(ArrayList::new));
        // Product + 옵션 생성
        Product product = Product.create(
                request.getName(),
                request.getDescription(),
                options
        );

        Product savedProduct = productRepository.save(product);

        return new CreateProductResponse(savedProduct.getId());
    }

    public ProductsPageResponse getProducts(Pageable pageable) {
        return ProductsPageResponse.from(productRepository.findAll(pageable));
    }

    public ProductDetailResponse getProductDetail(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(ProductNotFoundException::new);
        List<ProductOption> productOptions = product.getProductOptions();

        return ProductDetailResponse.from(
                product, productOptions
        );
    }
}
