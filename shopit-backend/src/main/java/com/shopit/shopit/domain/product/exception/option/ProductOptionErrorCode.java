package com.shopit.shopit.domain.product.exception.option;

import com.shopit.shopit.global.common.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductOptionErrorCode implements ErrorCode {
    IMAGE_FILE_REQUIRED(4101, "이미지 파일 누락", 400),
    INVALID_IMAGE_FORMAT(4102, "허용되지 않은 이미지 형식", 400),
    IMAGE_SIZE_EXCEEDED(4103, "이미지 크기 초과", 400),

    OPTION_IMAGE_ALREADY_EXISTS(4104, "옵션 이미지 이미 존재", 409),
    NOT_FOUND(4105, "옵션을 찾을 수 없음", 400),

    IMAGE_STORAGE_FAILED(9100, "이미지 저장 실패", 500);

    private final int code;
    private final String message;
    private final int httpStatus;
}