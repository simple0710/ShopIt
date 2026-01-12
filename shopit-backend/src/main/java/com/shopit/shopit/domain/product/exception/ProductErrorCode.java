package com.shopit.shopit.domain.product.exception;

import com.shopit.shopit.global.common.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductErrorCode implements ErrorCode {

    PRODUCT_NOT_FOUND(4000, "상품 없음", 404),
    PRODUCT_NOT_AVAILABLE(4001, "판매 불가 상품", 400),

    PRODUCT_OPTION_NOT_FOUND(4002, "옵션 없음", 404),
    OPTION_SOLD_OUT(4003, "옵션 품절", 400),

    INSUFFICIENT_STOCK(4004, "재고 부족", 400),
    STOCK_CONFLICT(4005, "재고 동시성 문제", 409),

    PRODUCT_OPTION_REQUIRED(4010, "상품 옵션 누락", 400);

    private final int code;
    private final String message;
    private final int httpStatus;
}
