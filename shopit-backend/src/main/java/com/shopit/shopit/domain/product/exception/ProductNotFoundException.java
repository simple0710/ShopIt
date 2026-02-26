package com.shopit.shopit.domain.product.exception;

import com.shopit.shopit.global.common.exception.ServiceException;

public class ProductNotFoundException extends ServiceException {
    public ProductNotFoundException() {
        super(ProductErrorCode.PRODUCT_NOT_FOUND);
    }
}
