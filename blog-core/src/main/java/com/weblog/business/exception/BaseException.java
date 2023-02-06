package com.weblog.business.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseException extends Exception {
    int code;

    BaseException(String msg) {
        super(msg);
    }
}
