package com.weblog.framework.advice;

import lombok.Data;

public interface Result<T> {

    int getCode();

    @Data
    class Ok<T> implements Result<T> {
        int code = 0;
        T content;

        private Ok(T content) {
            this.setContent(content);
        }
    }

    @Data
    class Err<T> implements Result<T> {
        int code;
        String reason;

        private Err(int code, String reason) {
            this.code = code;
            this.reason = reason;
        }
    }

    static <T> Result<T> ok(T content) {
        return new Ok<>(content);
    }

    static <T> Result<T> err(int code, String reason) {
        return new Err<>(code, reason);
    }
}
