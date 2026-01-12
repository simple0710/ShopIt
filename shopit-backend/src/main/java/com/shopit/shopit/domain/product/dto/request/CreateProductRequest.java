package com.shopit.shopit.domain.product.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CreateProductRequest {
    private String name;
    private String description;
    private List<ProductOptionRequest> productOptions;
}