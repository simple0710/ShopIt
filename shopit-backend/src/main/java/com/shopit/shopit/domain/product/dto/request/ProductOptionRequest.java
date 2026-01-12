package com.shopit.shopit.domain.product.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProductOptionRequest {
    private String name;
    private String optionValue;
    private Long price;
    private Integer quantity;
    private String imageUrl;
    private Double discountRate;
}