package com.shopit.shopit.domain.user.exception;

import com.shopit.shopit.global.common.exception.ServiceException;;

public class DuplicateEmailException extends ServiceException {

    public DuplicateEmailException() {
        super(UserErrorCode.DUPLICATE_EMAIL);
    }
}