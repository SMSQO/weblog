package com.weblog.business.exception;


public class EntityNotFoundException extends BaseException {

    public EntityNotFoundException(String msg) {
        super(msg);
        setCode(3);
    }
}
