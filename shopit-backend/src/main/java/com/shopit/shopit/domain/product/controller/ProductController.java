package com.shopit.shopit.domain.product.controller;

import com.shopit.shopit.domain.product.dto.request.CreateProductRequest;
import com.shopit.shopit.domain.product.dto.request.ProductStatusRequest;
import com.shopit.shopit.domain.product.dto.response.CreateProductResponse;
import com.shopit.shopit.domain.product.dto.response.ProductDetailResponse;
import com.shopit.shopit.domain.product.dto.response.ProductResponse;
import com.shopit.shopit.domain.product.dto.response.ProductsPageResponse;
import com.shopit.shopit.domain.product.service.ProductService;
import com.shopit.shopit.global.common.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ApiResponse<CreateProductResponse>> createProduct(
            @RequestBody CreateProductRequest request
    ) {
        return ResponseEntity.ok(
                ApiResponse.success(productService.createProduct(request))
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<ProductsPageResponse>> getProducts(
            @PageableDefault(size = 20) Pageable pageable
    ) {
        return ResponseEntity.ok(
                ApiResponse.success(productService.getProducts(pageable))
        );
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponse<ProductDetailResponse>> getProductDetail(
            @PathVariable Long productId
    ) {
        return ResponseEntity.ok(
                ApiResponse.success(productService.getProductDetail(productId))
        );
    }

    @PatchMapping("/{productId}/status")
    public ResponseEntity<ApiResponse<Object>> patchProductStatus(
            @PathVariable Long productId,
            @RequestBody @Valid ProductStatusRequest request
    ) {

        productService.patchProductStatus(productId, request);

        return ResponseEntity.ok(
                ApiResponse.success(null)
        );
    }

}
