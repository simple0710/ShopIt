package com.shopit.shopit.domain.product.exception.option;

import com.shopit.shopit.global.common.exception.ServiceException;

public class ProductOptionNotFoundException extends ServiceException {

    public ProductOptionNotFoundException() {
        super(ProductOptionErrorCode.NOT_FOUND);
    }
}
