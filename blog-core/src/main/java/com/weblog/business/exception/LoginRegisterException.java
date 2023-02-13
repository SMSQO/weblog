package com.weblog.business.exception;

public class LoginRegisterException extends BaseException {

    public LoginRegisterException(String msg) {
        super(msg);
        setCode(4);
    }
}
