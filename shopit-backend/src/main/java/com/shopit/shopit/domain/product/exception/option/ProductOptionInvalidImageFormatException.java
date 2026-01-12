package com.shopit.shopit.domain.product.exception.option;

import com.shopit.shopit.global.common.exception.ServiceException;

public class ProductOptionInvalidImageFormatException extends ServiceException {

    public ProductOptionInvalidImageFormatException() {
        super(ProductOptionErrorCode.INVALID_IMAGE_FORMAT);
    }
}