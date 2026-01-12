package com.shopit.shopit.domain.product.service;

import com.shopit.shopit.domain.product.dto.request.CreateProductRequest;
import com.shopit.shopit.domain.product.dto.response.CreateProductResponse;
import com.shopit.shopit.domain.product.entity.Inventory;
import com.shopit.shopit.domain.product.entity.Product;
import com.shopit.shopit.domain.product.entity.ProductOption;
import com.shopit.shopit.domain.product.exception.ProductOptionRequiredException;
import com.shopit.shopit.domain.product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

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
}
