package com.weblog.business.exception;

public class EmptyAttachmentException extends BaseException {

    public EmptyAttachmentException() {
        super("Uploaded file could not be empty");
        setCode(6);
    }
}
