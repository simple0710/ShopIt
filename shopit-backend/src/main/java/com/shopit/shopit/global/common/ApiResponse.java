package com.shopit.shopit.global.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiResponse<T> {

    private boolean success;
    private T data;
    private String message;
    private Integer errorCode;
    private Integer httpStatus;

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, data, null, null, 200);
    }

    public static <T> ApiResponse<T> fail(String message, int errorCode, int httpStatus) {
        return new ApiResponse<>(false, null, message, errorCode, httpStatus);
    }
}
