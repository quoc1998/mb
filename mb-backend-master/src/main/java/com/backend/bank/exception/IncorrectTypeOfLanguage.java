package com.backend.bank.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CREATED)
public class IncorrectTypeOfLanguage extends RuntimeException {
    public IncorrectTypeOfLanguage(String reason) {
        super(reason);
    }
}
