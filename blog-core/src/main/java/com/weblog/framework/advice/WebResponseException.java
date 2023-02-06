package com.weblog.framework.advice;

import com.weblog.business.exception.BaseException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class WebResponseException {

    @ExceptionHandler(value = Throwable.class)
    public Object exceptionHandler(Exception e) {
        if (e instanceof BaseException) {
            return Result.err(((BaseException) e).getCode(), e.getMessage());
        } else {
            e.printStackTrace();
            return Result.err(-1, e.getMessage());
        }
    }
}
