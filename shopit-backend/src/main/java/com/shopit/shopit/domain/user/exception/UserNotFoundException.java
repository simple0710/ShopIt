package com.shopit.shopit.domain.user.exception;

import com.shopit.shopit.global.common.exception.ServiceException;

public class UserNotFoundException extends ServiceException {

    public UserNotFoundException() {
        super(UserErrorCode.USER_NOT_FOUND);
    }
}
