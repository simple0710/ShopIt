package com.shopit.shopit.domain.product.exception;

import com.shopit.shopit.global.common.exception.ServiceException;

public class ProductOptionRequiredException extends ServiceException {

    public ProductOptionRequiredException() {
        super(ProductErrorCode.PRODUCT_OPTION_REQUIRED);
    }
}
