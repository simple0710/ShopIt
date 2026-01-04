package com.shopit.shopit.global.common.exception;

public interface ErrorCode {
    int getCode();
    String getMessage();
    int getHttpStatus();
}
