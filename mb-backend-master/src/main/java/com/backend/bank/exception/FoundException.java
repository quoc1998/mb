package com.backend.bank.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FOUND)
public class FoundException extends RuntimeException {
    public FoundException(String reason) {
        super(reason);
    }
}
