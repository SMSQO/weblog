package com.weblog.business.exception;

public class ReviewException extends BaseException {

    public ReviewException(String msg) {
        super(msg);
        setCode(2);
    }
}
