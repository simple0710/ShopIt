package com.shopit.shopit.domain.product.dto.response;

import com.shopit.shopit.domain.product.entity.Product;
import com.shopit.shopit.domain.product.entity.ProductOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class ProductDetailResponse {

    private Long productId;
    private String productName;
    private String description;

    List<ProductOptionResponse> options = new ArrayList<>();

    public static ProductDetailResponse from(Product product, List<ProductOption> productOptions) {

        List<ProductOptionResponse> optionResponses =
                productOptions.stream()
                        .map(ProductOptionResponse::from)
                        .toList();

        return new ProductDetailResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                optionResponses
        );
    }
}
