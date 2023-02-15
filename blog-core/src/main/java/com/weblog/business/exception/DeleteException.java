package com.weblog.business.exception;

public class DeleteException extends BaseException {

    public DeleteException(String msg) {
        super(msg);
        setCode(2);
    }
}
