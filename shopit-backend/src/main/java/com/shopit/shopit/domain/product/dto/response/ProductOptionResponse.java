package com.shopit.shopit.domain.product.dto.response;

import com.shopit.shopit.domain.product.entity.ProductOption;
import com.shopit.shopit.type.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ProductOptionResponse {

    // 색상 / 사이즈 등
    private String optionName;
    // Red / XL 등
    private String optionValue;
    private Long price;
    private ProductStatus status;

    public static ProductOptionResponse from(ProductOption productOption) {
        return new ProductOptionResponse(
                productOption.getOptionName(),
                productOption.getOptionValue(),
                productOption.getPrice(),
                ProductStatus.APPROVED
        );
    }
}
