package com.moler.technicaltask.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
public class BasketIsClosedRunTimeException extends RuntimeException {
    public BasketIsClosedRunTimeException(String message) {
    }
}
