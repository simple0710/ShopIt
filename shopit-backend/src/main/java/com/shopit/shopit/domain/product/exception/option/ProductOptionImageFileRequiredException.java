package com.shopit.shopit.domain.product.exception.option;

import com.shopit.shopit.global.common.exception.ServiceException;

public class ProductOptionImageFileRequiredException extends ServiceException {

    public ProductOptionImageFileRequiredException() {
        super(ProductOptionErrorCode.IMAGE_FILE_REQUIRED);
    }
}