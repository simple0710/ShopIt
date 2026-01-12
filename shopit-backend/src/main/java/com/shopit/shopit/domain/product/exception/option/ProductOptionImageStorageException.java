package com.shopit.shopit.domain.product.exception.option;

import com.shopit.shopit.global.common.exception.ServiceException;

public class ProductOptionImageStorageException extends ServiceException {

    public ProductOptionImageStorageException() {
        super(ProductOptionErrorCode.IMAGE_STORAGE_FAILED);
    }
}