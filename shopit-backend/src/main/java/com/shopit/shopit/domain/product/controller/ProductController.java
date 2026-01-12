package com.shopit.shopit.domain.product.controller;

import com.shopit.shopit.domain.product.dto.request.CreateProductRequest;
import com.shopit.shopit.domain.product.dto.response.CreateProductResponse;
import com.shopit.shopit.domain.product.service.ProductService;
import com.shopit.shopit.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
