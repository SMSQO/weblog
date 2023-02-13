package com.weblog.business.exception;

public class PermissionDeniedException extends BaseException {

    public PermissionDeniedException() {
        super("Permission denied: You are not allowed to visit this API.");
        setCode(2);
    }
}
