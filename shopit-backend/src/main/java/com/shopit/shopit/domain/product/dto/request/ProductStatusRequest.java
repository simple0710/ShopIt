package com.shopit.shopit.domain.product.dto.request;

import com.shopit.shopit.type.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductStatusRequest {
    ProductStatus status;
}
