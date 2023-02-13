package com.weblog.business.exception;

public class NotLoggedInException extends BaseException {

    public NotLoggedInException() {
        super("Not logged in yet");
        setCode(1);
    }
}
