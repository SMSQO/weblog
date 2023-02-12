package com.weblog.business.exception;

public class SameContactException extends BaseException {
    public SameContactException(String msg) {
        super(msg);
        setCode(5);
    }
}
